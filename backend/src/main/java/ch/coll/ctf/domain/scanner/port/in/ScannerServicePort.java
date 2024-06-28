package ch.coll.ctf.domain.scanner.port.in;

import org.springframework.web.multipart.MultipartFile;

public interface ScannerServicePort {
  public void scanFile(MultipartFile file);
}
