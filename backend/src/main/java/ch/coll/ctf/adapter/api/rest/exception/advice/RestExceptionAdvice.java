package ch.coll.ctf.adapter.api.rest.exception.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ch.coll.ctf.adapter.api.rest.exception.assembler.RestExceptionAssembler;
import ch.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class RestExceptionAdvice {
  private final RestExceptionAssembler assembler;

  @ResponseBody
  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public RestExceptionResponse handleRuntimeException(RuntimeException exception) {
    return assembler.toModel(exception);
  }
}
