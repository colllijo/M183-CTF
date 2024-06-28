package ch.coll.ctf.domain.ctf.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.multipart.MultipartFile;

import ch.coll.ctf.domain.ctf.model.Ctf;
import ch.coll.ctf.domain.ctf.port.in.CtfServicePort;
import ch.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import ch.coll.ctf.domain.scanner.port.in.ScannerServicePort;
import ch.coll.ctf.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CtfService implements CtfServicePort {
  private final CtfRepositoryPort ctfRepositoryPort;
  private final ScannerServicePort scannerService;

  @Value("${config.upload-dir:uploads}")
  private String uploadDir;

  @Override
  public List<Ctf> getAllCtfs() {
    return ctfRepositoryPort.findAll();
  }

  @Override
  public Ctf getCtfByName(String name) {
    return ctfRepositoryPort.findByName(name)
        .orElseThrow(() -> new IllegalArgumentException("Ctf with name " + name + " not found"));
  }

  @Override
  public Ctf createCtf(Ctf ctf, MultipartFile attachment) {
    User author = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    ctf.setAuthor(author);

    if (attachment != null && !attachment.isEmpty()) {
      scannerService.scanFile(attachment);
      ctf.setFilePath(saveFileToDisk(attachment));
    }

    return ctfRepositoryPort.save(ctf);
  }

  @Override
  public Ctf updateCtf(String name, Ctf ctf) {
    if (ctfRepositoryPort.findByName(name).isEmpty()) {
      throw new IllegalArgumentException("Ctf with name " + name + " does not exist");
    }
    return ctfRepositoryPort.save(ctf);
  }

  @Override
  public void deleteCtf(String name) {
    ctfRepositoryPort.deleteByName(name);
  }

  private String saveFileToDisk(MultipartFile attachment) {
    Path uploadsPath = Paths.get(uploadDir);
    if (!uploadsPath.toFile().exists()) {
      if (!uploadsPath.toFile().mkdirs()) throw new RuntimeException("Could not create uploads directory");
    }

    String filename = String.format("%s-%s", UUID.randomUUID().toString(), attachment.getOriginalFilename());
    Path filePath = Paths.get(uploadDir, filename);
    if (!filePath.toFile().exists()) {
      try {
        Files.copy(attachment.getInputStream(), filePath);
        return filename;
      } catch (Exception e) {
        throw new RuntimeException("Could not save file to disk", e);
      }
    } else {
      throw new RuntimeException("Could not save file to disk");
    }
  }
}
