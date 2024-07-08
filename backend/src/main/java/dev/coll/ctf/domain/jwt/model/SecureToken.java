package dev.coll.ctf.domain.jwt.model;

import java.util.Base64;

import lombok.Builder;

@Builder
public record SecureToken (String value, String fingerprint, Integer lifeTime) {
  public String getFingerprintBase64() {
    return Base64.getEncoder().encodeToString(fingerprint.getBytes());
  }
}
