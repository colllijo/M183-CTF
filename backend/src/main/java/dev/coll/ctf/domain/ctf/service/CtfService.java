package dev.coll.ctf.domain.ctf.service;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import dev.coll.ctf.domain.ctf.model.Ctf;
import dev.coll.ctf.domain.ctf.model.Solve;
import dev.coll.ctf.domain.ctf.model.exception.BadFlagException;
import dev.coll.ctf.domain.ctf.model.exception.CtfNotFoundException;
import dev.coll.ctf.domain.ctf.port.in.CtfAttachmentServicePort;
import dev.coll.ctf.domain.ctf.port.in.CtfServicePort;
import dev.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import dev.coll.ctf.domain.scanner.port.in.ScannerServicePort;
import dev.coll.ctf.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CtfService implements CtfServicePort {
  private final CtfRepositoryPort ctfRepository;
  private final CtfAttachmentServicePort ctfAttachmentService;
  private final ScannerServicePort scannerService;

  @Override
  public List<Ctf> getAllCtfs() {
    return ctfRepository.getCtfs();
  }

  @Override
  public Optional<Ctf> getCtfByName(String name) {
    return ctfRepository.getCtfByName(name);
  }

  @Override
  public Ctf createCtf(Ctf ctf, MultipartFile attachment) {
    User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ctf.setAuthor(author);

    if (attachment != null && !attachment.isEmpty()) {
      scannerService.scanFile(attachment);
      ctf.setFilePath(ctfAttachmentService.saveFile(ctf.getName(), attachment));
    }

    return ctfRepository.createCtf(ctf);
  }

  @Override
  public ByteArrayResource downloadFile(String filePath) {
    return ctfAttachmentService.loadFile(filePath);
  }

  @Override
  public Solve submitFlag(String name, String flag) {
    Ctf ctf = ctfRepository.getCtfByName(name).orElseThrow(() -> new CtfNotFoundException(name));

    if (!ctf.getFlag().equals(flag)) throw new BadFlagException(name);

    User solver = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Solve solve = Solve.builder()
        .ctf(ctf)
        .solver(solver)
        .points(100)
        .rank(ctf.getSolves().size() + 1)
        .build();

    return ctfRepository.createSolve(solve);
  }

  @Override
  public Ctf updateCtf(String name, Ctf ctf) {
    return ctfRepository.updateCtf(ctf);
  }

  @Override
  public void deleteCtf(String name) {
    ctfRepository.deleteCtfByName(name);
  }
}
