package dev.coll.ctf.domain.iam.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import dev.coll.ctf.domain.iam.model.authentication.AuthenticationTokens;
import dev.coll.ctf.domain.iam.model.exception.InvalidRefreshTokenException;
import dev.coll.ctf.domain.iam.port.in.AuthenticationServicePort;
import dev.coll.ctf.domain.jwt.model.SecureToken;
import dev.coll.ctf.domain.jwt.port.in.JwtServicePort;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServicePort {
  private final AuthenticationManager authenticationManager;
  private final JwtServicePort jwtService;
  private final UserServicePort userService;

  @Override
  public AuthenticationTokens register(User registrant) {
    User user = userService.createUser(registrant);

    return generateAuthenticationTokens(user);
  }

  @Override
  public AuthenticationTokens login(String username, String password) {
    Authentication authentication = authenticationManager
      .authenticate(new UsernamePasswordAuthenticationToken(username, password));

    if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
      return generateAuthenticationTokens((User) authentication.getPrincipal());
    }

    return null;
  }

  @Override
  public AuthenticationTokens refresh(SecureToken refreshToken) {
    User user = userService.getUserByUsername(jwtService.extractUsername(refreshToken.value()))
      .orElse(null);

    if (!jwtService.isTokenValid(refreshToken, user)) throw new InvalidRefreshTokenException();

    return refreshAccessToken(user);
  }

  private AuthenticationTokens generateAuthenticationTokens(User user) {
    return AuthenticationTokens.builder()
      .accessToken(jwtService.generateSecureAccessToken(user))
      .refreshToken(jwtService.generateSecureRefreshToken(user))
      .build();
  }

  private AuthenticationTokens refreshAccessToken(User user) {
    return AuthenticationTokens.builder()
      .accessToken(jwtService.generateSecureAccessToken(user))
      .build();
  }
}
