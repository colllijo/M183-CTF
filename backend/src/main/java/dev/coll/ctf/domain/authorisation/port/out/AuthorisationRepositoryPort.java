package dev.coll.ctf.domain.authorisation.port.out;

import java.util.List;
import java.util.Optional;

import dev.coll.ctf.domain.authorisation.model.Permission;
import dev.coll.ctf.domain.authorisation.model.Role;

public interface AuthorisationRepositoryPort {
  List<Role> getRoles();

  Optional<Role> getRoleByName(String name);

  Role createRole(Role role);

  Role updateRole(Role role);

  void delteRoleByName(String name);

  void delteRole(Role role);

  List<Permission> getPermissions();

  Optional<Permission> getPermissionByName(String name);

  Permission createPermission(Permission permission);
}
