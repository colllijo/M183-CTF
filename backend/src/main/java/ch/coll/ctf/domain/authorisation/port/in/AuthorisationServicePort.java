package ch.coll.ctf.domain.authorisation.port.in;

import java.util.List;

import ch.coll.ctf.domain.authorisation.model.Permission;
import ch.coll.ctf.domain.authorisation.model.Role;
import ch.coll.ctf.domain.user.model.User;

public interface AuthorisationServicePort {
  List<Role> getRoles();

  Role createRole(Role role);

  void delteRole(Role role);

  User addRoleToUser(String username, String roleName);

  User removeRoleFromUser(String username, String roleName);

  List<Permission> getPermissions();

  Role addPermissionToRole(Role role, Permission permission);

  Role removePermissionFromRole(Role role, Permission permission);
}
