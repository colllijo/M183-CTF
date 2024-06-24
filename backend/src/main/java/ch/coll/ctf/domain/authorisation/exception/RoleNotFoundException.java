package ch.coll.ctf.domain.authorisation.exception;

public class RoleNotFoundException extends RuntimeException {
  public RoleNotFoundException(Long id) {
    super(String.format("Role not found: Role w/ id=%d not found", id));
  }

  public RoleNotFoundException(String username) {
    super(String.format("Role not found: Role w/ ame=%s not found", username));
  }
}
