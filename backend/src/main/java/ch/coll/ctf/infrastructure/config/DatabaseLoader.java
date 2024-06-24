package ch.coll.ctf.infrastructure.config;

import java.util.EnumSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import ch.coll.ctf.domain.authorisation.model.DefaultRoles;
import ch.coll.ctf.domain.authorisation.model.Permissions;
import ch.coll.ctf.domain.authorisation.port.out.AuthorisationRepositoryPort;
import ch.coll.ctf.domain.user.model.User;
import ch.coll.ctf.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseLoader implements ApplicationRunner {
  private final UserRepositoryPort userRepository;
  private final AuthorisationRepositoryPort authorisationRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${ctf.config.default-admin.username:admin}")
  private String defaultAdminUsername;
  @Value("${ctf.config.default-admin.password:admin}")
  private String defaultAdminPassword;

  public void run(ApplicationArguments args) {
    loadPermissions();
    loadDefaultRoles();

    loadDefaultAdminUser();
  }

  private void loadDefaultRoles() {
    EnumSet.allOf(DefaultRoles.class).stream()
      .map(DefaultRoles::getRole)
      .filter(role -> authorisationRepository.getRoleByName(role.getName()).isEmpty())
      .forEach(role -> authorisationRepository.createRole(role));
  }

  private void loadPermissions() {
    EnumSet.allOf(Permissions.class).stream()
      .map(Permissions::getPermission)
      .filter(permission -> authorisationRepository.getPermissionByName(permission.getName()).isEmpty())
      .forEach(permission -> authorisationRepository.createPermission(permission));
  }

  private void loadDefaultAdminUser() {
    if (!userRepository.getUserByUsername(defaultAdminUsername).isEmpty()) return;

    User admin = User.builder()
        .username(defaultAdminUsername)
        .password(passwordEncoder.encode(defaultAdminPassword)).email("info@ctf.com").build();
    admin.getRoles().add(DefaultRoles.ADMIN.getRole());

    userRepository.createUser(admin);
  }
}
