package ch.coll.ctf.domain.authentication.exception;

public class InvalidRefreshTokenException extends RuntimeException {
  public InvalidRefreshTokenException() {
    super("Refresh token is invalid.");
  }
}
