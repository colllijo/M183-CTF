package ch.coll.ctf.domain.user.service;

import java.util.Optional;

import ch.coll.ctf.domain.user.model.User;
import ch.coll.ctf.domain.user.port.in.UserServicePort;
import ch.coll.ctf.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService implements UserServicePort {

  private final UserRepositoryPort userRepository;

  public Optional<User> getUserByUsername(String username) {
    return userRepository.getUserByUsername(username);
  }

  public User createUser(User user) {
    return userRepository.createUser(user);
  }
}
