package dev.coll.ctf.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.coll.ctf.domain.ctf.port.in.CtfServicePort;
import dev.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import dev.coll.ctf.domain.ctf.service.CtfService;
import dev.coll.ctf.domain.iam.port.in.AdministrationServicePort;
import dev.coll.ctf.domain.iam.port.in.AuthenticationServicePort;
import dev.coll.ctf.domain.iam.port.in.AuthorisationServicePort;
import dev.coll.ctf.domain.iam.port.out.AuthorisationRepositoryPort;
import dev.coll.ctf.domain.iam.service.AdministrationService;
import dev.coll.ctf.domain.iam.service.AuthenticationService;
import dev.coll.ctf.domain.iam.service.AuthorisationService;
import dev.coll.ctf.domain.jwt.port.in.JwtServicePort;
import dev.coll.ctf.domain.jwt.service.JwtService;
import dev.coll.ctf.domain.scanner.port.in.ScannerServicePort;
import dev.coll.ctf.domain.scanner.port.out.ScannerPort;
import dev.coll.ctf.domain.scanner.service.ScannerService;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import dev.coll.ctf.domain.user.port.out.UserRepositoryPort;
import dev.coll.ctf.domain.user.service.UserService;

@Configuration
public class BeanConfig {
  @Bean
  public UserServicePort userService(PasswordEncoder passwordEncoder, UserRepositoryPort userRepository) {
    return new UserService(passwordEncoder, userRepository);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
  }

  @Bean
  public CtfServicePort ctfService(CtfRepositoryPort ctfRepository, ScannerServicePort scannerService) {
    return new CtfService(ctfRepository, scannerService);
  }

  @Bean
  public ScannerServicePort scannerService(ScannerPort scannerPort) {
    return new ScannerService(scannerPort);
  }

  @Bean
  public JwtServicePort jwtService() {
    return new JwtService();
  }

  @Bean
  public AuthenticationServicePort authenticationService(UserServicePort userService, JwtServicePort jwtService, AuthenticationManager authenticationManager) {
    return new AuthenticationService(authenticationManager, jwtService, userService);
  }

  @Bean
  public AuthorisationServicePort authorisationService() {
    return new AuthorisationService();
  }

  @Bean
  public AdministrationServicePort administrationService(AuthorisationRepositoryPort authorisationRepository, UserRepositoryPort userRepository) {
    return new AdministrationService(authorisationRepository, userRepository);
  }
}
