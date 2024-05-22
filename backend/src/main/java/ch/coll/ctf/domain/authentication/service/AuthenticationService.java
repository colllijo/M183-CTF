package ch.coll.ctf.domain.authentication.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import ch.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
import ch.coll.ctf.domain.token.model.SecureToken;
import ch.coll.ctf.domain.token.port.in.JwtServicePort;
import ch.coll.ctf.domain.user.model.User;
import ch.coll.ctf.domain.user.port.in.UserServicePort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServicePort {
  private final UserServicePort userService;
  private final JwtServicePort jwtService;
  private final AuthenticationManager authenticationManager;

  @Override
  public SecureToken login(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    User user = userService.getUserByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));

    String fingerprint = UUID.randomUUID().toString();
    return new SecureToken(jwtService.generateToken(user, hashFingerprint(fingerprint)), fingerprint);
  }

  @Override
  public SecureToken register(User registrationUser) {
    User user = userService.createUser(registrationUser);

    String fingerprint = UUID.randomUUID().toString();
    return new SecureToken(jwtService.generateToken(user, hashFingerprint(fingerprint)), fingerprint);
  }

  private String hashFingerprint(String fingerprint) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] fingerprintBytes = digest.digest(fingerprint.getBytes());

      return Base64.getEncoder().encodeToString(fingerprintBytes);
    } catch (NoSuchAlgorithmException e) {
      return fingerprint;
    }
  }

  @Override
  public Integer getExpirationTime() {
    return jwtService.getExpirationTime();
  }
}
