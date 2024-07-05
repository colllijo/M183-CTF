package dev.coll.ctf.domain.scanner.service;

import org.springframework.web.multipart.MultipartFile;

import dev.coll.ctf.domain.scanner.port.in.ScannerServicePort;
import dev.coll.ctf.domain.scanner.port.out.ScannerPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ScannerService implements ScannerServicePort {
  private final ScannerPort scannerPort;

  public void scanFile(MultipartFile file) {
    scannerPort.scanFile(file);
  }
}
