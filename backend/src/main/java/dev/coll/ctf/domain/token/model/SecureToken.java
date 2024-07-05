package dev.coll.ctf.domain.token.model;

import java.util.Base64;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecureToken {
  private String token;
  private String fingerprint;

  public String getFingerprintBase64() {
    return Base64.getEncoder().encodeToString(fingerprint.getBytes());
  }
}
