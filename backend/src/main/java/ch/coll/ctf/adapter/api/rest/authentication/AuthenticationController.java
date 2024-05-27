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
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationServicePort authenticationService;

  private final RegistrationRequestMapper registrationMapper;

  @PostMapping("/login")
  public AuthenticatedResponse login(@RequestBody AuthenticationRequest authenticationRequest,
      HttpServletResponse response) {
    SecureToken token = authenticationService.login(authenticationRequest.getUsername(),
        authenticationRequest.getPassword());
    Cookie cookie = new Cookie("Access-Token", token.getFingerprint());
    cookie.setPath("/");
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(authenticationService.getExpirationTime());

    response.addCookie(cookie);
    return new AuthenticatedResponse(token.getToken());
  }

  @PostMapping("/register")
  public AuthenticatedResponse register(@RequestBody RegistrationRequest user,
      HttpServletResponse response) {
    if (!Objects.equals(user.getPassword(), user.getPasswordConfirmation()))
      throw new IllegalArgumentException("Passwords do not match");

    SecureToken token = authenticationService.register(registrationMapper.mapRequestToUser(user));
    Cookie cookie = new Cookie("Access-Token", token.getFingerprint());
    cookie.setPath("/");
    cookie.setSecure(true);
    cookie.setHttpOnly(true);
    cookie.setMaxAge(authenticationService.getExpirationTime());

    response.addCookie(cookie);
    return new AuthenticatedResponse(token.getToken());
  }
}
