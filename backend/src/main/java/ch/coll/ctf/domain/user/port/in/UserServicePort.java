package ch.coll.ctf.domain.user.port.in;

import java.util.Optional;

import ch.coll.ctf.domain.user.model.User;

public interface UserServicePort {
  public Optional<User> getUserByUsername(String username);

  public User createUser(User user);
}
