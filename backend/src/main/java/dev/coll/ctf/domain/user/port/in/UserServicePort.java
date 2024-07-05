package dev.coll.ctf.domain.user.port.in;

import java.util.List;
import java.util.Optional;

import dev.coll.ctf.domain.user.model.User;

public interface UserServicePort {
  List<User> getUsers();

  Optional<User> getUserByUsername(String username);

  User createUser(User user);

  User updateUser(User user);
}
