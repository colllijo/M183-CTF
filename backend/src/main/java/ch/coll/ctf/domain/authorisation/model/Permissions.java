package ch.coll.ctf.domain.authorisation.model;

import java.util.HashSet;

import lombok.Getter;

@Getter
public enum Permissions {
  READ_USERS("READ_USERS", "Permission to read inforamtion about all users.");

  private final Permission permission;

  private Permissions(String name, String description) {
    this.permission = Permission.builder().name(name).description(description).roles(new HashSet<>()).build();
  }

  public String getName() {
    return permission.getName();
  }
}
