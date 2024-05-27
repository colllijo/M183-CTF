package ch.coll.ctf.domain.authentication.service;

import java.util.List;
import java.util.Objects;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import ch.coll.ctf.domain.user.model.User;
import ch.coll.ctf.domain.user.port.in.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationProviderService implements AuthenticationProvider {
  private final UserServicePort userService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final String username = authentication.getName();
    final String password = authentication.getCredentials().toString();

    User user = userService.getUserByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));

    if (!Objects.equals(user.getPassword(), password))
      throw new BadCredentialsException("Invalid credentials");
    else
      return new UsernamePasswordAuthenticationToken(user, password, List.of());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
