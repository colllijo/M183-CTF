package dev.coll.ctf.domain.ctf.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import dev.coll.ctf.domain.ctf.model.Ctf;
import dev.coll.ctf.domain.ctf.model.Solve;
import dev.coll.ctf.domain.ctf.model.exception.BadFlagException;
import dev.coll.ctf.domain.ctf.model.exception.CtfNotFoundException;
import dev.coll.ctf.domain.ctf.port.in.CtfServicePort;
import dev.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import dev.coll.ctf.domain.scanner.port.in.ScannerServicePort;
import dev.coll.ctf.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CtfService implements CtfServicePort {
  private final CtfRepositoryPort ctfRepositoryPort;
  private final ScannerServicePort scannerService;

  @Value("${config.upload-dir:uploads}")
  private String uploadDir;

  @Override
  public List<Ctf> getAllCtfs() {
    return ctfRepositoryPort.getCtfs();
  }

  @Override
  public Ctf getCtfByName(String name) {
    return ctfRepositoryPort.getCtfByName(name)
        .orElseThrow(() -> new IllegalArgumentException("Ctf with name " + name + " not found"));
  }

  @Override
  public Ctf createCtf(Ctf ctf, MultipartFile attachment) {
    User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ctf.setAuthor(author);

    if (attachment != null && !attachment.isEmpty()) {
      scannerService.scanFile(attachment);
      ctf.setFilePath(saveFileToDisk(ctf.getName(), attachment));
    }

    return ctfRepositoryPort.createCtf(ctf);
  }

  @Override
  public ByteArrayResource downloadFile(String filePath) {
    Path file = Paths.get(uploadDir, filePath);
    if (file.toFile().exists()) {
      try {
        return new ByteArrayResource(Files.readAllBytes(file));
      } catch (Exception e) {
        throw new RuntimeException("Could not read file from disk", e);
      }
    } else {
      throw new IllegalArgumentException("File with path " + filePath + " not found");
    }
  }

  @Override
  public Solve submitFlag(String name, String flag) {
    Ctf ctf = ctfRepositoryPort.getCtfByName(name).orElseThrow(() -> new CtfNotFoundException(name));

    if (!ctf.getFlag().equals(flag)) throw new BadFlagException(name);

    User solver = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    Solve solve = Solve.builder()
        .ctf(ctf)
        .solver(solver)
        .points(100)
        .rank(ctf.getSolves().size() + 1)
        .build();

    return ctfRepositoryPort.createSolve(solve);
  }

  @Override
  public Ctf updateCtf(String name, Ctf ctf) {
    return ctfRepositoryPort.updateCtf(ctf);
  }

  @Override
  public void deleteCtf(String name) {
    ctfRepositoryPort.deleteCtfByName(name);
  }

  private String saveFileToDisk(String ctfName, MultipartFile attachment) {
    Path uploadsPath = Paths.get(uploadDir);
    if (!uploadsPath.toFile().exists()) {
      if (!uploadsPath.toFile().mkdirs()) throw new RuntimeException("Could not create uploads directory");
    }

    Path directoryPath = Paths.get(uploadDir, ctfName);
    Path filePath = Paths.get(uploadDir, ctfName, attachment.getOriginalFilename());

    if (!directoryPath.toFile().exists()) {
      if (!directoryPath.toFile().mkdirs()) throw new RuntimeException("Could not create directory for ctf");
    }
    if (!filePath.toFile().exists()) {
      try {
        Files.copy(attachment.getInputStream(), filePath);
        return Paths.get(ctfName, attachment.getOriginalFilename()).toString();
      } catch (Exception e) {
        throw new RuntimeException("Could not save file to disk", e);
      }
    } else {
      throw new RuntimeException("Could not save file to disk");
    }
  }
}
