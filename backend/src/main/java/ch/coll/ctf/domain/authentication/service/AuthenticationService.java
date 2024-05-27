package ch.coll.ctf.domain.authentication.service;

import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import ch.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
import ch.coll.ctf.domain.token.model.SecureToken;
import ch.coll.ctf.domain.token.port.in.JwtServicePort;
import ch.coll.ctf.domain.user.model.User;
import ch.coll.ctf.domain.user.port.in.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServicePort {
  private final UserServicePort userService;
  private final JwtServicePort jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public SecureToken login(String username, String password) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, password));

    String fingerprint = UUID.randomUUID().toString();
    return new SecureToken(
        jwtService.generateToken((User) authentication.getPrincipal(), jwtService.hashFingerprint(fingerprint)),
        fingerprint);
  }

  @Override
  public SecureToken register(User registrationUser) {
    User user = userService.createUser(registrationUser);

    String fingerprint = UUID.randomUUID().toString();
    return new SecureToken(jwtService.generateToken(user, jwtService.hashFingerprint(fingerprint)), fingerprint);
  }

  @Override
  public Integer getExpirationTime() {
    return jwtService.getExpirationTime();
  }
}
