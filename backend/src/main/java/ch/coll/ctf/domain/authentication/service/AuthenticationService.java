package ch.coll.ctf.domain.authentication.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import ch.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
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
  public String login(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

    User user = userService.getUserByUsername(username)
        .orElseThrow(() -> new IllegalArgumentException("User not found"));
    return jwtService.generateToken(user);
  }

  @Override
  public String register(User registrationUser) {
    User user = userService.createUser(registrationUser);
    return jwtService.generateToken(user);
  }
}
