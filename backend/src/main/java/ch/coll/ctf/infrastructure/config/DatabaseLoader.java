package ch.coll.ctf.infrastructure.config;

import java.util.EnumSet;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import ch.coll.ctf.domain.authorisation.model.DefaultRoles;
import ch.coll.ctf.domain.authorisation.model.Permissions;
import ch.coll.ctf.domain.authorisation.port.out.AuthorisationRepositoryPort;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DatabaseLoader implements ApplicationRunner {
  private final AuthorisationRepositoryPort authorisationRepository;

  public void run(ApplicationArguments args) {
    loadPermissions();
    loadDefaultRoles();
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
}
