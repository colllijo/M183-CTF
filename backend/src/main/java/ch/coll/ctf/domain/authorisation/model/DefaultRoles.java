package ch.coll.ctf.domain.authorisation.model;

import lombok.Getter;

@Getter
public enum DefaultRoles {
  USER("USER", "User role"),
  ADMIN("ADMIN", "Admin role");

  private final Role role;

  private DefaultRoles(String name, String description) {
    this.role = Role.builder().name(name).description(description).build();
  }

  public String getName() {
    return role.getName();
  }
}
