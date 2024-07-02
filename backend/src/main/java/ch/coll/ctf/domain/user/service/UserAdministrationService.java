package ch.coll.ctf.domain.user.service;

import java.util.List;
import java.util.Optional;

import ch.coll.ctf.domain.user.model.User;
import ch.coll.ctf.domain.user.port.in.UserAdministrationServicePort;
import ch.coll.ctf.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserAdministrationService implements UserAdministrationServicePort {
  private final UserRepositoryPort userRepository;

  public List<User> getUsers() {
    return userRepository.getUsers();
  }

  public Optional<User> getUserByUsername(String username) {
    return userRepository.getUserByUsername(username);
  }

  public User updateUser(User user) {
    return userRepository.updateUser(user);
  }
}
