package ch.coll.ctf.domain.authentication.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import ch.coll.ctf.domain.user.model.User;
import ch.coll.ctf.domain.user.port.in.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {
  private final UserServicePort userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final String username = authentication.getName();
    final String password = authentication.getCredentials().toString();

    Optional<User> userOptional = userService.getUserByUsername(username);
    if (userOptional.isEmpty()) {
      passwordEncoder.matches("42", "TestString");
      throw new BadCredentialsException("Invalid credentials");
    }

    User user = userOptional.get();

    if (passwordEncoder.matches(password, user.getPassword()))
      return new UsernamePasswordAuthenticationToken(user, password, List.of());
    else
      throw new BadCredentialsException("Invalid credentials");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
