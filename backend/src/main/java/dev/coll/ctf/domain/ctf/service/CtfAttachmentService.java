package dev.coll.ctf.domain.ctf.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import dev.coll.ctf.domain.ctf.port.in.CtfAttachmentServicePort;

public class CtfAttachmentService implements CtfAttachmentServicePort {
  @Value("${config.upload-dir:uploads}")
  private String uploadDir;

  @Override
  public String saveFile(String name, MultipartFile attachment) {
    Path uploadsPath = Paths.get(uploadDir);
    if (!uploadsPath.toFile().exists()) {
      if (!uploadsPath.toFile().mkdirs()) throw new RuntimeException("Could not create uploads directory");
    }

    Path directoryPath = Paths.get(uploadDir, name);
    Path filePath = Paths.get(uploadDir, name, attachment.getOriginalFilename());

    if (!directoryPath.toFile().exists()) {
      if (!directoryPath.toFile().mkdirs()) throw new RuntimeException("Could not create directory for ctf");
    }
    if (!filePath.toFile().exists()) {
      try {
        Files.copy(attachment.getInputStream(), filePath);
        return Paths.get(name, attachment.getOriginalFilename()).toString();
      } catch (Exception e) {
        throw new RuntimeException("Could not save file to disk", e);
      }
    } else {
      throw new RuntimeException("Could not save file to disk");
    }
  }

  @Override
  public ByteArrayResource loadFile(String filePath) {
    Path file = Paths.get(uploadDir, filePath);
    if (file.toFile().exists()) {
      try {
        return new ByteArrayResource(Files.readAllBytes(file));
      } catch (Exception e) {
        throw new RuntimeException("Could not read file from disk", e);
      }
    } else {
      throw new RuntimeException("File not found");
    }
  }
}
