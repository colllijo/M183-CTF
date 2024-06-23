package ch.coll.ctf.domain.authentication.port.in;

import java.util.Map;

import ch.coll.ctf.domain.authentication.exception.UnauthorizedException;
import ch.coll.ctf.domain.token.model.SecureToken;
import ch.coll.ctf.domain.user.model.User;

public interface AuthenticationServicePort {
  public Map<String, SecureToken> login(String username, String password);

  public Map<String, SecureToken> register(User registrationUser);

  public SecureToken refresh(String refreshToken, String refreshFingerprint);

  public Integer getAccessExpirationTime();

  public Integer getRefreshExpirationTime();

  public void checkFeatureAccess(String feature) throws UnauthorizedException;
}
