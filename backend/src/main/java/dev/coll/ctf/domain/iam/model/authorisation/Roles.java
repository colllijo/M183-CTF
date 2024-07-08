package dev.coll.ctf.domain.iam.model.authorisation;

import java.util.HashSet;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum Roles {
  USER("USER", "User role",
    Permissions.SOLVE_CHALLENGE
  ),
  AUTHOR("AUTHOR", "Author role",
    Permissions.CREATE_CHALLENGE
  ),
  ADMIN("ADMIN", "Admin role",
    Permissions.READ_USERS,
    Permissions.READ_ROLES,
    Permissions.READ_PERMISSIONS,
    Permissions.MODIFY_USER_ROLES,
    Permissions.CREATE_CHALLENGE,
    Permissions.SOLVE_CHALLENGE
  );

  private final Role role;

  private Roles(String name, String description, Permissions... permissions) {
    this.role = Role.builder()
        .name(name)
        .description(description)
        .permissions(new HashSet<>(Stream.of(permissions).map(Permissions::getPermission).toList()))
    .build();
  }

  public String getName() {
    return role.getName();
  }

  public String getAuthorityName() {
    return "ROLE_" + role.getName();
  }
}
