package ch.coll.ctf.domain.authorisation.model;

import java.util.Arrays;
import java.util.HashSet;

import lombok.Getter;

@Getter
public enum DefaultRoles {
  USER("USER", "User role"),
  AUTHOR("AUTHOR", "Author role"),
  ADMIN("ADMIN", "Admin role",
    Permissions.READ_USERS.getPermission(),
    Permissions.READ_ROLES.getPermission(),
    Permissions.READ_PERMISSIONS.getPermission(),
    Permissions.MODIFY_USER_ROLES.getPermission()
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
