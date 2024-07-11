package dev.coll.ctf.adapter.api.rest.exception.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import dev.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
@Order(Ordered.LOWEST_PRECEDENCE)
public class RestExceptionAdvice {
  @ResponseBody
  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  public RestExceptionResponse handleRuntimeException(RuntimeException exception) {
    log.error(exception.getMessage(), exception);

    return RestExceptionResponse.builder()
      .error(exception.getClass().getSimpleName())
      .message(exception.getMessage())
      .status(500)
    .build();
  }

  @ResponseBody
  @ExceptionHandler(AccessDeniedException.class)
  @ResponseStatus(HttpStatus.FORBIDDEN)
  @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  public RestExceptionResponse handleAccessDeniedException(AccessDeniedException exception) {
    return RestExceptionResponse.builder()
      .error(exception.getClass().getSimpleName())
      .message(exception.getMessage())
      .status(403)
    .build();
  }
}
