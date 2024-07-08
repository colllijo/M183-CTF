package dev.coll.ctf.domain.iam.model.exception;

import lombok.Getter;

@Getter
public class UnauthenticatedException extends RuntimeException {
  public UnauthenticatedException() {
    super("User is not authenticated");
  }
}
