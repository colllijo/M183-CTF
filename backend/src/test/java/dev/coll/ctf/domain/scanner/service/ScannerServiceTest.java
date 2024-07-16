package dev.coll.ctf.domain.scanner.service;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MultipartFile;

import dev.coll.ctf.domain.scanner.port.out.ScannerPort;


class ScannerServiceTest {
  private ScannerService testee;

  private ScannerPort mockScanner;

  @BeforeEach
  void setup() {
    mockScanner = mock(ScannerPort.class);

    testee = new ScannerService(mockScanner);
  }

  @Test
  void scanFileShouldCallScannerPort() {
    // Given
    MultipartFile file = mock(MultipartFile.class);

    // When
    testee.scanFile(file);

    // Then
    verify(mockScanner).scanFile(file);
  }
}
