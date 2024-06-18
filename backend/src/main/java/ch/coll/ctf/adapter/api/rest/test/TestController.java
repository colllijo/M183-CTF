package ch.coll.ctf.adapter.api.rest.test;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/test")
public class TestController {
  @GetMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE)
  @ApiResponse(responseCode = "200", description = "User authenticated successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<String> test() {
    return ResponseEntity.ok("{ \"message\": \"Hello, World!\" }");
  }
}
