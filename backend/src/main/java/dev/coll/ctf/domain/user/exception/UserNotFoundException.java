package dev.coll.ctf.domain.user.exception;

public class UserNotFoundException extends RuntimeException {
  public UserNotFoundException(Long id) {
    super(String.format("User not found: User w/ id=%d not found", id));
  }

  public UserNotFoundException(String username) {
    super(String.format("User not found: User w/ username=%s not found", username));
  }
}
