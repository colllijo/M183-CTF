package dev.coll.ctf.domain.ctf.model.exception;

public class BadFlagException extends RuntimeException {
  public BadFlagException(String name) {
    super(String.format("Flag for Ctf %s is incorrect", name));
  }
}
