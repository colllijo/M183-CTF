package ch.coll.ctf.adapter.api.rest.authentication.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class AuthenticationAdvice {
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String validationViolationExceptionHandler(ConstraintViolationException e) {
    return e.getMessage();
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String validationViolationExceptionHandler(IllegalArgumentException e) {
    return e.getMessage();
  }
}
