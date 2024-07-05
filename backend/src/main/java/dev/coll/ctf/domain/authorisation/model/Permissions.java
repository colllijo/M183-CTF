package dev.coll.ctf.domain.authorisation.model;

import java.util.HashSet;

import lombok.Getter;

@Getter
public enum Permissions {
  READ_USERS("READ_USERS", "Permission to read inforamtion about all users."),
  READ_ROLES("READ_ROLES", "Permission to read all roles."),
  READ_PERMISSIONS("READ_PERMISSIONS", "Permission to read all permissions."),
  MODIFY_USER_ROLES("MODIFY_USER_ROLES", "Permission to modify user roles."),
  CREATE_CHALLENGE("CREATE_CHALLENGE", "Permission to create a new challenge."),
  SOLVE_CHALLENGE("SOLVE_CHALLENGE", "Permission to solve a challenge.");

  private final Permission permission;

  private Permissions(String name, String description) {
    this.permission = Permission.builder().name(name).description(description).roles(new HashSet<>()).build();
  }

  public String getName() {
    return permission.getName();
  }
}
