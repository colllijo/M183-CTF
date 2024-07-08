package dev.coll.ctf.domain.iam.port.in;

import dev.coll.ctf.domain.iam.model.authentication.AuthenticationTokens;
import dev.coll.ctf.domain.jwt.model.SecureToken;
import dev.coll.ctf.domain.user.model.User;

/**
 * Interface used for user authentication.<br/>
 * This interfaces allows user to register, log in and refresh their tokens.
 *
 * @author Liam, Metzger
 * @version 1.0
 */
public interface AuthenticationServicePort {
  /**
   * @param user
   * @return
   */
  public AuthenticationTokens register(User user);

  /**
   * @param username
   * @param password
   * @return
   */
  public AuthenticationTokens login(String username, String password);

  /**
   * @param refreshToken
   * @return
   */
  public AuthenticationTokens refresh(SecureToken refreshToken);
}
