package dev.coll.ctf.domain.jwt.port.in;

import dev.coll.ctf.domain.jwt.model.SecureToken;
import dev.coll.ctf.domain.user.model.User;

/**
 * Interface used for JWT token generation and validation.<br/>
 *
 * @author Liam, Metzger
 * @version 1.0
 */
public interface JwtServicePort {
  /**
   * @param user
   * @return
   */
  public SecureToken generateSecureAccessToken(User user);

  /**
   * @param user
   * @return
   */
  public SecureToken generateSecureRefreshToken(User user);

  /**
   * @param token
   * @param user
   * @return
   */
  public boolean isTokenValid(SecureToken token, User user);

  /**
   * @param token
   * @return
   */
  public String extractUsername(String token);
}
