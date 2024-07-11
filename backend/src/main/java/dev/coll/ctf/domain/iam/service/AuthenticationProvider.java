package dev.coll.ctf.domain.iam.service;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.coll.ctf.domain.iam.port.in.AuthenticationProviderPort;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationProvider implements AuthenticationProviderPort {
  // Argon hash of '', which cannot be a user password.
  private static final User UNFOUND_USER = User.builder().password("$argon2id$v=19$m=16,t=2,p=1$dThzN0xxcGxjUkkzbVRnRw$NAbYxxsxCQ+oB+A76VaFHQ").build();

  private final UserServicePort userService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    final String username = authentication.getName();
    final String password = authentication.getCredentials().toString();

    User user = userService.getUserByUsername(username).orElse(UNFOUND_USER);

    if (passwordEncoder.matches(password, user.getPassword())) return new UsernamePasswordAuthenticationToken(user, password, userService.getAuthorities(user));
    else throw new BadCredentialsException("Invalid credentials");
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return authentication.equals(UsernamePasswordAuthenticationToken.class);
  }
}
