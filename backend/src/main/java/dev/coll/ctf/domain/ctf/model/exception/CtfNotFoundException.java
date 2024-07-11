package dev.coll.ctf.domain.ctf.model.exception;

public class CtfNotFoundException extends RuntimeException {
  public CtfNotFoundException(Long id) {
    super(String.format("Ctf not found: Ctf w/ id=%d not found", id));
  }

  public CtfNotFoundException(String name) {
    super(String.format("Ctf not found: Ctf w/ name=%s not found", name));
  }
}
