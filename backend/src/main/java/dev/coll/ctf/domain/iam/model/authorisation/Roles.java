package dev.coll.ctf.domain.iam.model.authorisation;

import java.util.HashSet;
import java.util.List;

import lombok.Getter;

@Getter
public enum Roles {
  USER("USER", "User role",
    Permission.SUBMIT_FLAG
  ),
  AUTHOR("AUTHOR", "Author role",
    Permission.CREATE_CHALLENGE
  ),
  ADMIN("ADMIN", "Admin role", Permission.values());

  private final Role role;

  private Roles(String name, String description, Permission... permissions) {
    this.role = Role.builder()
        .name(name)
        .description(description)
        .permissions(new HashSet<>(List.of(permissions)))
    .build();
  }

  public String getName() {
    return role.getName();
  }

  public String getAuthorityName() {
    return "ROLE_" + role.getName();
  }
}
