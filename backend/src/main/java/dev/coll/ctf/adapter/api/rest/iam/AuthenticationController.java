package dev.coll.ctf.adapter.api.rest.iam;

import java.util.Arrays;

import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import dev.coll.ctf.adapter.api.rest.iam.dto.AuthenticatedResponse;
import dev.coll.ctf.adapter.api.rest.iam.dto.AuthenticationRequest;
import dev.coll.ctf.adapter.api.rest.iam.dto.RefreshRequest;
import dev.coll.ctf.adapter.api.rest.iam.dto.RegistrationRequest;
import dev.coll.ctf.adapter.api.rest.iam.mapper.RegistrationRequestMapper;
import dev.coll.ctf.domain.iam.model.authentication.AuthenticationTokens;
import dev.coll.ctf.domain.iam.model.authorisation.Feature;
import dev.coll.ctf.domain.iam.model.exception.UnauthenticatedException;
import dev.coll.ctf.domain.iam.port.in.AuthenticationServicePort;
import dev.coll.ctf.domain.iam.port.in.AuthorisationServicePort;
import dev.coll.ctf.domain.jwt.model.SecureToken;
import dev.coll.ctf.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationServicePort authenticationService;
  private final AuthorisationServicePort authorisationService;

  private final RegistrationRequestMapper registrationMapper;

  @ApiResponse(responseCode = "200", description = "User is authenticated")
  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public AuthenticatedResponse isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    SecurityContextHolder.getContext();

    if (authentication != null & authentication.getPrincipal() instanceof User) return new AuthenticatedResponse(((User) authentication.getPrincipal()).getUsername(), null);
    throw new UnauthenticatedException();
  }

  @ApiResponse(responseCode = "200", description = "User authenticated successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PostMapping(path = "/login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public AuthenticatedResponse login(@RequestBody AuthenticationRequest authenticationRequest,
      HttpServletResponse response) {
    log.info("Login request - username={}", authenticationRequest.getUsername());

    AuthenticationTokens tokens = authenticationService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
    return createTokenResponse(tokens, response);
  }

  @ApiResponse(responseCode = "200", description = "User authenticated successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PostMapping(path = "/register", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  public AuthenticatedResponse register(@Valid @RequestBody RegistrationRequest user,
      HttpServletResponse response) {
    log.info("Register request - username={}", user.getUsername());

    AuthenticationTokens tokens = authenticationService.register(registrationMapper.mapRequestToUser(user));
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

    AuthenticationTokens tokens = authenticationService.refresh(SecureToken.builder().value(refreshRequest.getRefreshToken()).fingerprint(refreshFingerprint).build());
    return createTokenResponse(tokens, response);
  }

  @ApiResponse(responseCode = "200", description = "User authenticated successfully")
  @DeleteMapping(path = "/logout")
  public void logout(HttpServletResponse response) {
    response.addCookie(createFingerprintCookie("Access-Token", "", 0));
    response.addCookie(createFingerprintCookie("Refresh-Token", "", 0));
  }

  @ApiResponse(responseCode = "200", description = "User is authorised")
  @PostMapping(path = "/check/{feature}")
  public void checkFeatureAccess(@PathVariable Feature feature) {
    authorisationService.checkFeatureAccess(feature);
  }

  private AuthenticatedResponse createTokenResponse(AuthenticationTokens tokens, HttpServletResponse response) {
    AuthenticatedResponse authenticatedResponse = AuthenticatedResponse.builder().tokens(AuthenticatedResponse.Tokens.builder().build()).build();

    if (tokens.accessToken() != null) {
      authenticatedResponse.getTokens().setAccessToken(tokens.accessToken().value());
      response.addCookie(createFingerprintCookie("Access-Token", tokens.accessToken().fingerprint(), tokens.accessToken().lifeTime()));
    }
    if (tokens.refreshToken() != null) {
      authenticatedResponse.getTokens().setRefreshToken(tokens.refreshToken().value());
      response.addCookie(createFingerprintCookie("Refresh-Token", tokens.refreshToken().fingerprint(), tokens.refreshToken().lifeTime()));
    }

    return authenticatedResponse;
  }

  private Cookie createFingerprintCookie(String name, String fingerprint, int maxAge) {
    Cookie cookie = new Cookie(name, fingerprint);
    cookie.setPath("/");
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(maxAge / 1000); // Divide maxAge by 1000 to convert milliseconds to seconds

    return cookie;
  }

  @Override
  public String toString() {
    return "AuthenticationController []";
  }
}
