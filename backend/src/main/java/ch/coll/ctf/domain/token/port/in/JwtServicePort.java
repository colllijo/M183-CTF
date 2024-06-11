package ch.coll.ctf.domain.token.port.in;

import ch.coll.ctf.domain.user.model.User;

public interface JwtServicePort {
  public String extractUsername(String token);

  public String generateAccessToken(User user, String fingerprint);

  public String generateRefreshToken(User user, String fingerprint);

  public boolean isTokenValid(String token, String fingerprint, User user);

  public String hashFingerprint(String fingerprint);

  public Integer getAccessExpirationTime();

  public Integer getRefreshExpirationTime();
}
