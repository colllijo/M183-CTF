package ch.coll.ctf.adapter.api.rest.authentication;

import java.util.Arrays;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.coll.ctf.adapter.api.rest.authentication.dto.AuthenticatedResponse;
import ch.coll.ctf.adapter.api.rest.authentication.dto.AuthenticationRequest;
import ch.coll.ctf.adapter.api.rest.authentication.dto.RefreshRequest;
import ch.coll.ctf.adapter.api.rest.authentication.dto.RegistrationRequest;
import ch.coll.ctf.adapter.api.rest.authentication.mapper.RegistrationRequestMapper;
import ch.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import ch.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
import ch.coll.ctf.domain.token.model.SecureToken;
import ch.coll.ctf.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationServicePort authenticationService;

  private final RegistrationRequestMapper registrationMapper;

  @ApiResponse(responseCode = "200", description = "User is authenticated")
  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public AuthenticatedResponse isAuthenticated() {
    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return new AuthenticatedResponse(user.getUsername(), null);
  }

  @ApiResponse(responseCode = "200", description = "User authenticated successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public AuthenticatedResponse login(@Valid @RequestBody AuthenticationRequest authenticationRequest,
      HttpServletResponse response) {
    Map<String, SecureToken> tokens = authenticationService.login(authenticationRequest.getUsername(),
        authenticationRequest.getPassword());

    return createTokenResponse(tokens, response);
  }

  @ApiResponse(responseCode = "200", description = "User authenticated successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public AuthenticatedResponse register(@Valid @RequestBody RegistrationRequest user,
      HttpServletResponse response) {
    Map<String, SecureToken> tokens = authenticationService.register(registrationMapper.mapRequestToUser(user));

    return createTokenResponse(tokens, response);
  }

  @ApiResponse(responseCode = "200", description = "Access token refreshed successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PostMapping(path = "/refresh", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public AuthenticatedResponse refresh(@Valid @RequestBody RefreshRequest refreshRequest, HttpServletRequest request,
      HttpServletResponse response) {
    String refreshFingerprint = null;
    Cookie[] cookies = request.getCookies();

    if (cookies != null) {
      refreshFingerprint = Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equals("Refresh-Token"))
          .map(Cookie::getValue)
          .findFirst()
          .orElse(null);
    }

    SecureToken token = authenticationService.refresh(refreshRequest.getRefreshToken(), refreshFingerprint);
    response.addCookie(createFingerprintCookie("Access-Token", token.getFingerprint(),
        authenticationService.getAccessExpirationTime()));

    return new AuthenticatedResponse(token.getToken(), null);
  }

  @ApiResponse(responseCode = "200", description = "User authenticated successfully")
  @DeleteMapping(path = "/logout")
  public void logout(HttpServletResponse response) {
    response.addCookie(createFingerprintCookie("Access-Token", "", 0));
    response.addCookie(createFingerprintCookie("Refresh-Token", "", 0));
  }

  private AuthenticatedResponse createTokenResponse(Map<String, SecureToken> tokens, HttpServletResponse response) {
    response.addCookie(createFingerprintCookie("Access-Token", tokens.get("Access-Token").getFingerprint(),
        authenticationService.getAccessExpirationTime()));
    response.addCookie(createFingerprintCookie("Refresh-Token", tokens.get("Refresh-Token").getFingerprint(),
        authenticationService.getRefreshExpirationTime()));

    return AuthenticatedResponse.builder()
      .tokens(
        AuthenticatedResponse.Tokens.builder()
          .accessToken(tokens.get("Access-Token").getToken())
          .refreshToken(tokens.get("Refresh-Token").getToken())
          .build()
        )
      .build();
  }

  private Cookie createFingerprintCookie(String name, String fingerprint, int maxAge) {
    Cookie cookie = new Cookie(name, fingerprint);
    cookie.setPath("/");
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(maxAge / 1000); // Divide maxAge by 1000 to convert milliseconds to seconds

    return cookie;
  }
}
