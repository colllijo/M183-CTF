package ch.coll.ctf.infrastructure.config;

import ch.coll.ctf.domain.ctf.port.in.SolveServicePort;
import ch.coll.ctf.domain.ctf.port.out.SolveRepositoryPort;
import ch.coll.ctf.domain.ctf.service.SolveService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import ch.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
import ch.coll.ctf.domain.authentication.service.AuthenticationService;
import ch.coll.ctf.domain.authorisation.port.in.AuthorisationServicePort;
import ch.coll.ctf.domain.authorisation.port.out.AuthorisationRepositoryPort;
import ch.coll.ctf.domain.authorisation.service.AuthorisationService;
import ch.coll.ctf.domain.ctf.port.in.CtfServicePort;
import ch.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import ch.coll.ctf.domain.ctf.service.CtfService;
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
  public CtfServicePort ctfService(CtfRepositoryPort ctfRepository) {
    return new CtfService(ctfRepository);
  }

  @Bean
  public AuthorisationServicePort authorisationService(AuthorisationRepositoryPort authorisationRepository, UserServicePort userService) {
    return new AuthorisationService(authorisationRepository, userService);
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
