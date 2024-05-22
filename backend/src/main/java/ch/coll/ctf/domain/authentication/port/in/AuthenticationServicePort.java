package ch.coll.ctf.domain.authentication.port.in;

import ch.coll.ctf.domain.token.model.SecureToken;
import ch.coll.ctf.domain.user.model.User;

public interface AuthenticationServicePort {

  public SecureToken login(String username, String password);

  public SecureToken register(User registrationUser);

  public Integer getExpirationTime();
}
