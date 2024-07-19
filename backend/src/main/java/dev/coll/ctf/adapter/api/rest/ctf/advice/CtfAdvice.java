package dev.coll.ctf.adapter.api.rest.ctf.advice;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import dev.coll.ctf.domain.ctf.model.exception.CtfNotFoundException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CtfAdvice {
  @ExceptionHandler(CtfNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  public RestExceptionResponse ctfNotFoundExceptionHandler(CtfNotFoundException exception) {
    return RestExceptionResponse.builder()
        .error(exception.getClass().getSimpleName())
        .message(exception.getMessage())
        .status(404)
        .build();
  }
}
