package dev.coll.ctf.domain.iam.service;

import java.util.List;
import java.util.Optional;

import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.iam.model.exception.RoleNotFoundException;
import dev.coll.ctf.domain.iam.port.in.AdministrationServicePort;
import dev.coll.ctf.domain.iam.port.out.AuthorisationRepositoryPort;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.model.exception.UserNotFoundException;
import dev.coll.ctf.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdministrationService implements AdministrationServicePort {
  private final AuthorisationRepositoryPort authorisationRepository;
  private final UserRepositoryPort userRepository;

  @Override
  public List<User> getUsers() {
    return userRepository.getUsers();
  }

  @Override
  public Optional<User> getUserByUsername(String username) {
    return userRepository.getUserByUsername(username);
  }

  @Override
  public User addRoleToUser(String username, String roleName) {
    User user = userRepository.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    Role role = authorisationRepository.getRoleByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));

    if (user.getRoles().contains(role)) {
      return user;
    }

    user.getRoles().add(role);
    return userRepository.updateUser(user);
  }

  @Override
  public User removeRoleFromUser(String username, String roleName) {
    User user = userRepository.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    Role role = authorisationRepository.getRoleByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));

    if (!user.getRoles().contains(role)) {
      return user;
    }

    user.getRoles().remove(role);
    return userRepository.updateUser(user);
  }

  @Override
  public List<Role> getRoles() {
    return authorisationRepository.getRoles();
  }

  @Override
  public Optional<Role> getRoleByName(String name) {
    return authorisationRepository.getRoleByName(name);
  }

  @Override
  public List<Permission> getPermissions() {
    return List.of(Permission.values());
  }
}
