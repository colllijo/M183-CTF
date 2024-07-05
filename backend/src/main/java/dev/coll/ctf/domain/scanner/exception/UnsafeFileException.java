package dev.coll.ctf.domain.scanner.exception;

public class UnsafeFileException extends RuntimeException {
  public UnsafeFileException() {
    super("To file to upload was deemed unsafe.");
  }
}
