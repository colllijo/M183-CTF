package dev.coll.ctf.domain.authorisation.service;

import java.util.List;
import java.util.Set;

import dev.coll.ctf.domain.authorisation.exception.RoleNotFoundException;
import dev.coll.ctf.domain.authorisation.model.Permission;
import dev.coll.ctf.domain.authorisation.model.Role;
import dev.coll.ctf.domain.authorisation.port.in.AuthorisationServicePort;
import dev.coll.ctf.domain.authorisation.port.out.AuthorisationRepositoryPort;
import dev.coll.ctf.domain.user.exception.UserNotFoundException;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorisationService implements AuthorisationServicePort {
  private final AuthorisationRepositoryPort authorisationRepository;
  private final UserServicePort userService;

  public List<Role> getRoles() {
    return authorisationRepository.getRoles();
  }

  public Role createRole(Role role) {
    return authorisationRepository.createRole(role);
  }

  public void delteRole(Role role) {
    authorisationRepository.delteRole(role);
  }

  public User addRoleToUser(String username, String roleName) {
    User user = userService.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    Role role = authorisationRepository.getRoleByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));

    Set<Role> roles = user.getRoles();
    roles.add(role);
    user.setRoles(roles);

    return userService.updateUser(user);
  }

  public User removeRoleFromUser(String username, String roleName) {
    User user = userService.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
    Role role = authorisationRepository.getRoleByName(roleName).orElseThrow(() -> new RoleNotFoundException(roleName));

    Set<Role> roles = user.getRoles();
    roles.remove(role);
    user.setRoles(roles);

    return userService.updateUser(user);
  }

  public List<Permission> getPermissions() {
    return authorisationRepository.getPermissions();
  }

  public Role addPermissionToRole(Role role, Permission permission) {
    return null;
  }

  public Role removePermissionFromRole(Role role, Permission permission) {
    return null;
  }
}
