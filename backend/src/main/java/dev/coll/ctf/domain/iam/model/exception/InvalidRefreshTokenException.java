package dev.coll.ctf.domain.iam.model.exception;

public class InvalidRefreshTokenException extends RuntimeException {
  public InvalidRefreshTokenException() {
    super("Refresh token is invalid.");
  }
}
