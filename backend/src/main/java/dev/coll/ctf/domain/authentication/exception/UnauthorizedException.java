package dev.coll.ctf.domain.authentication.exception;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String feature) {
    super(String.format("User is not authorized to access feature: %s", feature));
  }
}
