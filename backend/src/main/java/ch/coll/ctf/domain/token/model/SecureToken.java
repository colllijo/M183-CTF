package ch.coll.ctf.domain.token.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SecureToken {
  private String token;
  private String fingerprint;
}
