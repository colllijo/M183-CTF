package dev.coll.ctf.domain.authentication.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {
  private final UserServicePort userService;
  private final PasswordEncoder passwordEncoder;

  private final User unfoundUser = User.builder().password("***").build();

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final String username = authentication.getName();
    final String password = authentication.getCredentials().toString();

    User user = userService.getUserByUsername(username).orElse(unfoundUser);

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
