package ch.coll.ctf.adapter.api.rest.authentication;

import java.util.Objects;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.coll.ctf.adapter.api.rest.authentication.dto.AuthenticatedResponse;
import ch.coll.ctf.adapter.api.rest.authentication.dto.AuthenticationRequest;
import ch.coll.ctf.adapter.api.rest.authentication.dto.RegistrationRequest;
import ch.coll.ctf.adapter.api.rest.authentication.mapper.RegistrationRequestMapper;
import ch.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
import ch.coll.ctf.domain.token.model.SecureToken;
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

  @PostMapping("/login")
  public AuthenticatedResponse login(@Valid @RequestBody AuthenticationRequest authenticationRequest,
      HttpServletResponse response) {
    SecureToken token = authenticationService.login(authenticationRequest.getUsername(),
        authenticationRequest.getPassword());
    response.addCookie(
        createFingerprintCookie("Access-Token", token.getFingerprint(), authenticationService.getExpirationTime()));

    return new AuthenticatedResponse(token.getToken());
  }

  @PostMapping("/register")
  public AuthenticatedResponse register(@Valid @RequestBody RegistrationRequest user,
      HttpServletResponse response) {
    SecureToken token = authenticationService.register(registrationMapper.mapRequestToUser(user));
    response.addCookie(
        createFingerprintCookie("Access-Token", token.getFingerprint(), authenticationService.getExpirationTime()));

    return new AuthenticatedResponse(token.getToken());
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
