package ch.coll.ctf.adapter.api.rest.authentication.advice;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ch.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import ch.coll.ctf.domain.authentication.exception.UnauthenticatedException;
import ch.coll.ctf.domain.authentication.exception.UnauthorizedException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestControllerAdvice
@RequiredArgsConstructor
public class AuthenticationAdvice {
  @ResponseBody
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public RestExceptionResponse methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {
    return RestExceptionResponse.builder()
        .error(exception.getClass().getSimpleName())
        .status(400)
        .details(getDetails(exception))
        .build();
  }

  @ResponseBody
  @ExceptionHandler(UnauthenticatedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  public RestExceptionResponse handleUnauthenticatedException(UnauthenticatedException exception) {
    return RestExceptionResponse.builder()
        .error(exception.getClass().getSimpleName())
        .message(exception.getMessage())
        .status(401)
        .build();
  }

  @ResponseBody
  @ExceptionHandler({ UsernameNotFoundException.class, BadCredentialsException.class })
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  public RestExceptionResponse handleBadCredentials(Exception exception) {
    return RestExceptionResponse.builder()
        .error("Bad credentials")
        .message("Username and/or password are incorrect")
        .status(400)
        .build();
  }

  @ResponseBody
  @ExceptionHandler(UnauthorizedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  public RestExceptionResponse handleUnauthorizedException(UnauthorizedException exception) {
    return RestExceptionResponse.builder()
        .error(exception.getClass().getSimpleName())
        .message(exception.getMessage())
        .status(403)
        .build();
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
