package dev.coll.ctf.domain.iam.model.exception;

public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException(Long id) {
    super(String.format("Role not found: Role w/ id=%d not found", id));
  }

  public RoleNotFoundException(String username) {
    super(String.format("Role not found: Role w/ name=%s not found", username));
  }
}