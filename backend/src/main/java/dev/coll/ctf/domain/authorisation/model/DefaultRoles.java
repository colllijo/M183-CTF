package dev.coll.ctf.domain.authorisation.model;

import java.util.Arrays;
import java.util.HashSet;

import lombok.Getter;

@Getter
public enum DefaultRoles {
  USER("USER", "User role",
    Permissions.SOLVE_CHALLENGE.getPermission()
  ),
  AUTHOR("AUTHOR", "Author role",
    Permissions.CREATE_CHALLENGE.getPermission()
  ),
  ADMIN("ADMIN", "Admin role",
    Permissions.READ_USERS.getPermission(),
    Permissions.READ_ROLES.getPermission(),
    Permissions.READ_PERMISSIONS.getPermission(),
    Permissions.MODIFY_USER_ROLES.getPermission(),
    Permissions.CREATE_CHALLENGE.getPermission(),
    Permissions.SOLVE_CHALLENGE.getPermission()
  );

  private final Role role;

  private DefaultRoles(String name, String description, Permission... permissions) {
    this.role = Role.builder()
        .name(name)
        .description(description)
        .permissions(new HashSet<>(Arrays.asList(permissions)))
    .build();
  }

  public String getName() {
    return role.getName();
  }

  public String getAuthorityName() {
    return "ROLE_" + role.getName();
  }
}
