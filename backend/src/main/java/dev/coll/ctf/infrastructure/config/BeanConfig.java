package dev.coll.ctf.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
import dev.coll.ctf.domain.authentication.service.AuthenticationService;
import dev.coll.ctf.domain.authorisation.port.in.AuthorisationServicePort;
import dev.coll.ctf.domain.authorisation.port.out.AuthorisationRepositoryPort;
import dev.coll.ctf.domain.authorisation.service.AuthorisationService;
import dev.coll.ctf.domain.ctf.port.in.CtfServicePort;
import dev.coll.ctf.domain.ctf.port.in.SolveServicePort;
import dev.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import dev.coll.ctf.domain.ctf.port.out.SolveRepositoryPort;
import dev.coll.ctf.domain.ctf.service.CtfService;
import dev.coll.ctf.domain.ctf.service.SolveService;
import dev.coll.ctf.domain.scanner.port.in.ScannerServicePort;
import dev.coll.ctf.domain.scanner.port.out.ScannerPort;
import dev.coll.ctf.domain.scanner.service.ScannerService;
import dev.coll.ctf.domain.token.port.in.JwtServicePort;
import dev.coll.ctf.domain.token.service.JwtService;
import dev.coll.ctf.domain.user.port.in.UserAdministrationServicePort;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import dev.coll.ctf.domain.user.port.out.UserRepositoryPort;
import dev.coll.ctf.domain.user.service.UserAdministrationService;
import dev.coll.ctf.domain.user.service.UserService;

@Configuration
public class BeanConfig {
  @Bean
  public UserServicePort userService(UserRepositoryPort userRepository) {
    return new UserService(userRepository);
  }

  @Bean
  public UserAdministrationServicePort userAdministrationService(UserRepositoryPort userRepository) {
    return new UserAdministrationService(userRepository);
  }

  @Bean
  public CtfServicePort ctfService(CtfRepositoryPort ctfRepository, ScannerServicePort scannerService) {
    return new CtfService(ctfRepository, scannerService);
  }

  @Bean
  public SolveServicePort solveService(SolveRepositoryPort solveRepository) {
    return new SolveService(solveRepository);
  }

  @Bean
  public ScannerServicePort scannerService(ScannerPort scannerPort) {
    return new ScannerService(scannerPort);
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
