package dev.coll.ctf.domain.ctf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.refEq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import dev.coll.ctf.domain.ctf.model.Ctf;
import dev.coll.ctf.domain.ctf.model.Solve;
import dev.coll.ctf.domain.ctf.model.exception.BadFlagException;
import dev.coll.ctf.domain.ctf.model.exception.CtfNotFoundException;
import dev.coll.ctf.domain.ctf.port.in.CtfAttachmentServicePort;
import dev.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import dev.coll.ctf.domain.scanner.port.in.ScannerServicePort;
import dev.coll.ctf.domain.user.model.User;

class CtfServiceTest {
  private CtfService testee;

  private CtfRepositoryPort mockCtfRepositoryPort;
  private CtfAttachmentServicePort mockCtfAttachmentService;
  private ScannerServicePort mockScannerService;

  @BeforeEach
  void setup() {
    mockCtfRepositoryPort = mock(CtfRepositoryPort.class);
    mockCtfAttachmentService = mock(CtfAttachmentServicePort.class);
    mockScannerService = mock(ScannerServicePort.class);

    testee = new CtfService(mockCtfRepositoryPort, mockCtfAttachmentService, mockScannerService);
  }

  @Test
  void getAllCtfsShouldReturnCtfs() {
    // Given
    List<Ctf> ctfs = List.of(
      Ctf.builder().name("ctf1").build(),
      Ctf.builder().name("ctf2").build()
    );

    // When
    when(mockCtfRepositoryPort.getCtfs()).thenReturn(ctfs);

    List<Ctf> result = testee.getAllCtfs();

    // Then
    assertThat(result).isEqualTo(ctfs);
    verify(mockCtfRepositoryPort, times(1)).getCtfs();
  }

  @Test
  void getCtfByNameShouldReturnCtf() {
    // Given
    Ctf ctf = Ctf.builder().name("ctf1").build();

    // When
    when(mockCtfRepositoryPort.getCtfByName("ctf1")).thenReturn(Optional.of(ctf));

    Optional<Ctf> result = testee.getCtfByName("ctf1");

    // Then
    assertThat(result).isPresent().get().isEqualTo(ctf);
    verify(mockCtfRepositoryPort, times(1)).getCtfByName("ctf1");
  }

  @Test
  void createCtfShouldReturnNewCtf() {
    // Given
    User author = User.builder().username("user1").build();

    Ctf ctf = Ctf.builder().name("ctf1").build();
    Ctf expectedCtf = Ctf.builder().name("ctf1").author(author).build();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(author, null, null);
    SecurityContextHolder.getContext().setAuthentication(token);

    // When
    when(mockCtfRepositoryPort.createCtf(expectedCtf)).thenReturn(expectedCtf);

    Ctf result = testee.createCtf(ctf, null);

    // Then
    SecurityContextHolder.getContext().setAuthentication(authentication);

    assertThat(result).extracting("name", "author").containsExactly("ctf1", author);
    verify(mockCtfRepositoryPort, times(1)).createCtf(expectedCtf);
  }

  @Test
  void createCtfWithAttachmentShouldSaveIt() {
    // Given
    User author = User.builder().username("user1").build();

    Ctf ctf = Ctf.builder().name("ctf1").build();
    Ctf expectedCtf = Ctf.builder().name("ctf1").author(author).filePath("attachment.tar.gz").build();

    MultipartFile attachment = mock(MultipartFile.class);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(author, null, null);
    SecurityContextHolder.getContext().setAuthentication(token);

    // When
    when(mockCtfRepositoryPort.createCtf(expectedCtf)).thenReturn(expectedCtf);
    when(mockCtfAttachmentService.saveFile("ctf1", attachment)).thenReturn("attachment.tar.gz");

    Ctf result = testee.createCtf(ctf, attachment);

    // Then
    SecurityContextHolder.getContext().setAuthentication(authentication);

    assertThat(result).extracting("name", "author", "filePath").containsExactly("ctf1", author, "attachment.tar.gz");
    verify(mockCtfRepositoryPort, times(1)).createCtf(expectedCtf);
    verify(mockCtfAttachmentService, times(1)).saveFile(expectedCtf.getName(), attachment);
  }

  @Test
  void downloadFileShouldCallCtfAttachmentService() {
    // Given
    ByteArrayResource expectedAttachment = new ByteArrayResource("attachment".getBytes());

    // When
    when(mockCtfAttachmentService.loadFile("ctf1/attachment.tar.gz")).thenReturn(expectedAttachment);

    ByteArrayResource result= testee.downloadFile("ctf1/attachment.tar.gz");

    // Then
    assertThat(result).isNotNull().isEqualTo(expectedAttachment);
    verify(mockCtfAttachmentService, times(1)).loadFile("ctf1/attachment.tar.gz");
  }

  @Test
  void submitFlagShouldCreateSolve() {
    // Given
    String ctfName = "ctf1";
    String flag = "flag";

    Ctf ctf = Ctf.builder().name(ctfName).flag(flag).build();
    User solver = User.builder().username("user1").build();

    Solve expectedSolve = Solve.builder().ctf(ctf).solver(solver).points(100).rank(1).build();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(solver, null, null);
    SecurityContextHolder.getContext().setAuthentication(token);

    // When
    when(mockCtfRepositoryPort.getCtfByName(ctfName)).thenReturn(Optional.of(ctf));
    when(mockCtfRepositoryPort.createSolve(refEq(expectedSolve, "timestamp"))).thenReturn(expectedSolve);

    Solve result = testee.submitFlag(ctfName, flag);

    // Then
    SecurityContextHolder.getContext().setAuthentication(authentication);

    assertThat(result).isEqualTo(expectedSolve);
    verify(mockCtfRepositoryPort, times(1)).getCtfByName(ctfName);
    verify(mockCtfRepositoryPort, times(1)).createSolve(refEq(expectedSolve, "timestamp"));
  }

  @Test
  void submitFlagWithUnknowCtfThrowsException() {
    // When
    when(mockCtfRepositoryPort.getCtfByName(anyString())).thenReturn(Optional.empty());

    // Then
    assertThatThrownBy(() -> testee.submitFlag("ctf1", "flag")).isInstanceOf(CtfNotFoundException.class).hasMessage("Ctf not found: Ctf w/ name=ctf1 not found");
    verify(mockCtfRepositoryPort, times(1)).getCtfByName("ctf1");
  }

  @Test
  void submitFlagWithWrongFlagThrowsException() {
    // Given
    Ctf ctf = Ctf.builder().name("ctf1").flag("flag").build();

    // When
    when(mockCtfRepositoryPort.getCtfByName("ctf1")).thenReturn(Optional.of(ctf));

    // Then
    assertThatThrownBy(() -> testee.submitFlag("ctf1", "wrongFlag")).isInstanceOf(BadFlagException.class).hasMessage("Flag for Ctf ctf1 is incorrect");
    verify(mockCtfRepositoryPort, times(1)).getCtfByName("ctf1");
  }

  @Test
  void updateCtfShouldUpdate() {
    // Given
    Ctf ctf = Ctf.builder().name("ctf1").build();

    // When
    when(mockCtfRepositoryPort.updateCtf(ctf)).thenReturn(ctf);

    Ctf result = testee.updateCtf("ctf1", ctf);

    // Then
    assertThat(result).isEqualTo(ctf);
    verify(mockCtfRepositoryPort, times(1)).updateCtf(ctf);
  }

  @Test
  void deleteCtfShouldDelete() {
    // When
    testee.deleteCtf("ctf1");

    // Then
    verify(mockCtfRepositoryPort, times(1)).deleteCtfByName("ctf1");
  }
}
