package dev.coll.ctf.adapter.messaging.clamav;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import dev.coll.ctf.domain.scanner.exception.UnsafeFileException;
import dev.coll.ctf.domain.scanner.port.out.ScannerPort;
import xyz.capybara.clamav.ClamavClient;
import xyz.capybara.clamav.commands.scan.result.ScanResult;

@Component
public class ClamAVClientService implements ScannerPort {
  private ClamavClient client;

  public ClamAVClientService(@Value("${clamd.host:127.0.0.1}") String host, @Value("${clamd.port:3310}") int port) {
    this.client = new ClamavClient(host, port);
  }

  public void scanFile(MultipartFile file) {
    client.ping();

    try {
      ScanResult result = client.scan(file.getInputStream());

      if (result instanceof ScanResult.VirusFound) throw new UnsafeFileException();
    } catch (IOException e) {
      throw new RuntimeException("Error while scanning file");
    }
  }
}
