package dev.coll.ctf.domain.user.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.iam.model.authorisation.Roles;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import dev.coll.ctf.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService implements UserServicePort {
  private final PasswordEncoder passwordEncoder;
  private final UserRepositoryPort userRepository;

  @Override
  public List<User> getUsers() {
    return userRepository.getUsers().stream()
        .filter(user -> !user.getRoles().contains(Roles.ADMIN.getRole()))
        .toList();
  }

  @Override
  public Optional<User> getUserByUsername(String username) {
    return userRepository.getUserByUsername(username);
  }

  @Override
  public User createUser(User user) {
    user.setPassword(passwordEncoder.encode(user.getPassword()));

    return userRepository.createUser(user);
  }

  @Override
  public User updateUser(User user) {
    return userRepository.updateUser(user);
  }

  @Override
  public List<? extends GrantedAuthority> getAuthorities(User user) {
    return Stream.concat(
        user.getRoles().stream()
            .map(Role::getName)
            .map(role -> "ROLE_" + role),
        user.getPermissions().stream()
            .map(Permission::getName))
        .map(SimpleGrantedAuthority::new)
        .toList();
  }
}
