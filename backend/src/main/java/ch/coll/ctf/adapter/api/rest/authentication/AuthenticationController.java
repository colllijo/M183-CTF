package ch.coll.ctf.adapter.api.rest.authentication;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.coll.ctf.adapter.api.rest.authentication.dto.AuthenticatedResponse;
import ch.coll.ctf.adapter.api.rest.authentication.dto.AuthenticationRequest;
import ch.coll.ctf.adapter.api.rest.authentication.dto.RegistrationRequest;
import ch.coll.ctf.adapter.api.rest.authentication.mapper.RegistrationRequestMapper;
import ch.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import ch.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
import ch.coll.ctf.domain.token.model.SecureToken;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationServicePort authenticationService;

  private final RegistrationRequestMapper registrationMapper;

  @ApiResponse(responseCode = "200", description = "User authenticated successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public AuthenticatedResponse login(@Valid @RequestBody AuthenticationRequest authenticationRequest,
      HttpServletResponse response) {
    SecureToken token = authenticationService.login(authenticationRequest.getUsername(),
        authenticationRequest.getPassword());
    response.addCookie(
        createFingerprintCookie("Access-Token", token.getFingerprint(), authenticationService.getExpirationTime()));

    return new AuthenticatedResponse(token.getToken());
  }

  @ApiResponse(responseCode = "200", description = "User authenticated successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public AuthenticatedResponse register(@Valid @RequestBody RegistrationRequest user,
      HttpServletResponse response) {
    SecureToken token = authenticationService.register(registrationMapper.mapRequestToUser(user));
    Cookie test = createFingerprintCookie("Access-Token", token.getFingerprint(),
        authenticationService.getExpirationTime());
    System.out.println(test);
    System.out.println(test.getName());
    System.out.println(test.getValue());
    response.addCookie(test);

    return new AuthenticatedResponse(token.getToken());
  }

  @ApiResponse(responseCode = "200", description = "User authenticated successfully")
  @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @DeleteMapping(path = "/logout")
  public void logout(HttpServletResponse response) {
    response.addCookie(createFingerprintCookie("Access-Token", "", 0));
  }

  private Cookie createFingerprintCookie(String name, String fingerprint, int maxAge) {
    Cookie cookie = new Cookie(name, fingerprint);
    cookie.setPath("/");
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(maxAge);

    return cookie;
  }
}
