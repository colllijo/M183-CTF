package dev.coll.ctf.domain.user.port.in;

import java.util.List;
import java.util.Optional;

import dev.coll.ctf.domain.user.model.User;

public interface UserAdministrationServicePort {
  List<User> getUsers();

  Optional<User> getUserByUsername(String username);

  User updateUser(User user);
}