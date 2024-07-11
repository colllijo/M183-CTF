package dev.coll.ctf.domain.iam.model.authorisation;

import lombok.Getter;

@Getter
public enum Permission {
  READ_USERS("READ_USERS", "Permission to read inforamtion about all users."),
  READ_ROLES("READ_ROLES", "Permission to read all roles."),
  READ_PERMISSIONS("READ_PERMISSIONS", "Permission to read all permissions."),
  MODIFY_USER_ROLES("MODIFY_USER_ROLES", "Permission to modify user roles."),
  CREATE_CHALLENGE("CREATE_CHALLENGE", "Permission to create a new challenge."),
  SUBMIT_FLAG("SUBMIT_FLAG", "Permission to submit a flag for a challenge.");

  private final String name;
  private final String description;

  private Permission(final String name, final String description) {
    this.name = name;
    this.description = description;
  }
}
