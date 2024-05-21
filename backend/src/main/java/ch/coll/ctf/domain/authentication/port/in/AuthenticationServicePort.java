package ch.coll.ctf.domain.authentication.port.in;

import ch.coll.ctf.domain.user.model.User;

public interface AuthenticationServicePort {

  public String login(String username, String password);

  public String register(User registrationUser);
}
