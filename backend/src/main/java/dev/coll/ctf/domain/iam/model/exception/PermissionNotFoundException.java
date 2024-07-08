package dev.coll.ctf.domain.iam.model.exception;

public class PermissionNotFoundException extends RuntimeException {
  public PermissionNotFoundException(Long id) {
    super(String.format("Permission not found: Permission w/ id=%d not found", id));
  }

  public PermissionNotFoundException(String username) {
    super(String.format("Permission not found: Permission w/ ame=%s not found", username));
  }
}

