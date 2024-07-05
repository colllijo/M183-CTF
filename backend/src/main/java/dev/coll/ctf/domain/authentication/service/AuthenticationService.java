package dev.coll.ctf.domain.authentication.service;

import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.coll.ctf.domain.authentication.exception.InvalidRefreshTokenException;
import dev.coll.ctf.domain.authentication.exception.UnauthorizedException;
import dev.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
import dev.coll.ctf.domain.authorisation.model.DefaultRoles;
import dev.coll.ctf.domain.token.model.SecureToken;
import dev.coll.ctf.domain.token.port.in.JwtServicePort;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServicePort {
  private final UserServicePort userService;
  private final JwtServicePort jwtService;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Map<String, SecureToken> login(String username, String password) {
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(username, password));

    return generateTokens((User) authentication.getPrincipal());
  }

  @Override
  public Map<String, SecureToken> register(User registrationUser) {
    registrationUser.setPassword(passwordEncoder.encode(registrationUser.getPassword()));
    User user = userService.createUser(registrationUser);

    return generateTokens(user);
  }

  @Override
  public Map<String, SecureToken> refresh(String refreshToken, String refreshFingerprint) {
    User user = userService.getUserByUsername(jwtService.extractUsername(refreshToken))
        .orElse(null);

    if (!jwtService.isTokenValid(refreshToken, refreshFingerprint, user)) {
      throw new InvalidRefreshTokenException();
    }

    Map<String, SecureToken> tokens = new HashMap<>();

    String accessFingerprint = generateFingerprint();
    tokens.put("Access-Token",
        new SecureToken(jwtService.generateAccessToken(user, accessFingerprint), accessFingerprint));

    return tokens;
  }

  @Override
  public void checkFeatureAccess(String feature) throws UnauthorizedException {
    HashMap<String, List<String>> featurePermissions = new HashMap<>();
    featurePermissions.put("administration", List.of(
      DefaultRoles.ADMIN.getAuthorityName()
    ));
    featurePermissions.put("challenge-creation", List.of(
      DefaultRoles.ADMIN.getAuthorityName(),
      DefaultRoles.AUTHOR.getAuthorityName()
    ));

    List<String> authorites = SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
    for (String permission: featurePermissions.get(feature)) {
      if (authorites.contains(permission)) return;
    }

    throw new UnauthorizedException(feature);
  }

  @Override
  public Integer getAccessExpirationTime() {
    return jwtService.getAccessExpirationTime();
  }

  @Override
  public Integer getRefreshExpirationTime() {
    return jwtService.getRefreshExpirationTime();
  }

  private Map<String, SecureToken> generateTokens(User user) {
    Map<String, SecureToken> tokens = new HashMap<>();

    String accessFingerprint = generateFingerprint();
    String refreshFingerprint = generateFingerprint();

    tokens.put("Access-Token",
        new SecureToken(jwtService.generateAccessToken(user, accessFingerprint), accessFingerprint));
    tokens.put("Refresh-Token",
        new SecureToken(jwtService.generateRefreshToken(user, refreshFingerprint), refreshFingerprint));

    return tokens;
  }

  private String generateFingerprint() {
    return HexFormat.of().formatHex(KeyGenerators.secureRandom(256).generateKey());
  }
}
