package ch.coll.ctf.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import ch.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
import ch.coll.ctf.domain.authentication.service.AuthenticationService;
import ch.coll.ctf.domain.token.port.in.JwtServicePort;
import ch.coll.ctf.domain.token.service.JwtService;
import ch.coll.ctf.domain.user.port.in.UserServicePort;
import ch.coll.ctf.domain.user.port.out.UserRepositoryPort;
import ch.coll.ctf.domain.user.service.UserService;

@Configuration
public class BeanConfig {
  @Bean
  public UserServicePort userService(UserRepositoryPort userRepository) {
    return new UserService(userRepository);
  }

  @Bean
  public JwtServicePort jwtService() {
    return new JwtService();
  }

  @Bean
  public AuthenticationServicePort authenticationService(UserServicePort userService, JwtServicePort jwtService,
      AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
    return new AuthenticationService(userService, jwtService, authenticationManager, passwordEncoder);
  }
}