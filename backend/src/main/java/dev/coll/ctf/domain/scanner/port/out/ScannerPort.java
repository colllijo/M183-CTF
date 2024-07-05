package dev.coll.ctf.domain.scanner.port.out;

import org.springframework.web.multipart.MultipartFile;

public interface ScannerPort {
  public void scanFile(MultipartFile file);
}
