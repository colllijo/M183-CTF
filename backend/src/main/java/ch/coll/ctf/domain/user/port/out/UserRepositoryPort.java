package ch.coll.ctf.domain.user.port.out;

import java.util.Optional;

import ch.coll.ctf.domain.user.model.User;

public interface UserRepositoryPort {

  Optional<User> getUserById(Long id);

  Optional<User> getUserByUsername(String username);

  User createUser(User user);
}
