package ch.coll.ctf.domain.token.port.in;

import ch.coll.ctf.domain.user.model.User;

public interface JwtServicePort {
  public String extractUsername(String token);

  public String generateToken(User user, String fingerprint);

  public boolean isTokenValid(String token, User user);

  public Integer getExpirationTime();
}
