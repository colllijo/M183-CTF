package dev.coll.ctf.domain.ctf.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.io.FileWriter;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.ReflectionUtils;

class CtfAttachmentServiceTest {
  private CtfAttachmentService testee;

  private Path tmpDir;

  @BeforeEach
  void setup() {
    testee = new CtfAttachmentService();

    try {
      tmpDir = Files.createTempDirectory("uploads");

      Field field = ReflectionUtils.findField(CtfAttachmentService.class, "uploadDir");
      field.setAccessible(true);
      field.set(testee, tmpDir.toString());
    } catch (Exception e) {}
  }

  @AfterEach
  void cleanup() {
    try {
      FileSystemUtils.deleteRecursively(tmpDir.toFile());
    } catch (Exception e) {}
  }

  @Test
  void saveFileShouldSaveFile() {
    // Given
    String name = "ctf1";
    MockMultipartFile attachment = new MockMultipartFile("file", "file.txt", MediaType.TEXT_PLAIN_VALUE, "Hello, World!\n".getBytes());

    // When
    String result = testee.saveFile(name, attachment);

    // Then
    assertThat(result).isEqualTo("ctf1/file.txt");
    assertThat(Path.of(tmpDir.toString(), result)).exists().satisfies(path -> {
      assertThat(path.toFile()).hasContent("Hello, World!\n");
    });
  }

  @Test
  void loadFileShouldLoadFile() {
    try {
      // Given
      String filePath = "ctf1/file.txt";
      FileWriter writer = new FileWriter(tmpDir.toString() + filePath);
      writer.write("Hello, World!\n");
      writer.close();

      // When
      byte[] result = testee.loadFile(filePath).getByteArray();

      // Then
      assertThat(result).isEqualTo("Hello, World!\n".getBytes());
    } catch(Exception e) {}
  }

  @Test
  void loadFileForUnknownFileShouldThrowException() {
    // Given
    String filePath = "ctf1/file.txt";

    // Then
    assertThatThrownBy(() -> testee.loadFile(filePath)).isInstanceOf(RuntimeException.class).hasMessage("File not found");
  }
}
