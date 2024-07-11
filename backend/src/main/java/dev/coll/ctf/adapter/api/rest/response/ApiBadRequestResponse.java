package dev.coll.ctf.adapter.api.rest.response;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.http.MediaType;

import dev.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(
  responseCode = "400",
  description = "Bad request",
  content = @Content(
    mediaType = MediaType.APPLICATION_JSON_VALUE,
    schema = @Schema(
      name = "RestError",
      implementation = RestExceptionResponse.class
    )
  )
)
public @interface ApiBadRequestResponse {
}
