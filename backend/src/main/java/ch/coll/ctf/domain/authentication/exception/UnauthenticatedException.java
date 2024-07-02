package ch.coll.ctf.domain.authentication.exception;

import lombok.Getter;

@Getter
public class UnauthenticatedException extends RuntimeException {
  public UnauthenticatedException() {
    super("User is not authenticated");
  }
}
