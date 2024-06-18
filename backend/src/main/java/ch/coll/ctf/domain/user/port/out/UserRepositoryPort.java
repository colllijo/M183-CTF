package ch.coll.ctf.domain.user.port.out;

import java.util.List;
import java.util.Optional;

import ch.coll.ctf.domain.user.model.User;

public interface UserRepositoryPort {
  List<User> getUsers();

  Optional<User> getUserById(Long id);

  Optional<User> getUserByUsername(String username);

  User createUser(User user);
}
