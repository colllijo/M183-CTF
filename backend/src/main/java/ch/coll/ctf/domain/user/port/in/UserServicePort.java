package ch.coll.ctf.domain.user.port.in;

import java.util.List;
import java.util.Optional;

import ch.coll.ctf.domain.user.model.User;

public interface UserServicePort {
  public List<User> getUsers();

  public Optional<User> getUserByUsername(String username);

  public User createUser(User user);
}
