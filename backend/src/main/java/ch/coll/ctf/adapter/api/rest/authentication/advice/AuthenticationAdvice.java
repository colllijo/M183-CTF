package ch.coll.ctf.adapter.api.rest.authentication.advice;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ch.coll.ctf.adapter.api.rest.exception.assembler.RestExceptionAssembler;
import ch.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthenticationAdvice {
  private final RestExceptionAssembler exceptionAssembler;

  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public RestExceptionResponse validationViolationExceptionHandler(MethodArgumentNotValidException e) {
    return exceptionAssembler.toModel(e).setStatus(400).setMessage(null).setDetails(getDetails(e));
  }

  /**
   * Extracts the details of a {@link MethodArgumentNotValidException} and returns
   * them as a map.
   * The map contains the field names for field validations and the object name
   * for class validation as keys and a map of error codes and default
   * messages as values.
   *
   * @param e the exception to extract the details from
   * @return the details of the exception
   */
  private Map<String, Map<String, String>> getDetails(MethodArgumentNotValidException e) {
    return e.getBindingResult().getAllErrors().stream()
        .collect(Collectors.groupingBy(
            error -> error instanceof FieldError ? ((FieldError) error).getField() : error.getObjectName(),
            Collectors.toMap(ObjectError::getCode, ObjectError::getDefaultMessage)));
  }
}
