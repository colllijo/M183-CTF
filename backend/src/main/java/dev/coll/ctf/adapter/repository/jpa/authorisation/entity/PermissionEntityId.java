package dev.coll.ctf.adapter.repository.jpa.authorisation.entity;

import java.io.Serializable;

import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import lombok.Data;

@Data
public class PermissionEntityId implements Serializable {
  private Permission permission;
  private RoleEntity role;
}
