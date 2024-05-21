package ch.coll.ctf.adapter.api.rest.authentication;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.coll.ctf.adapter.api.rest.authentication.dto.AuthenticationRequest;
import ch.coll.ctf.adapter.api.rest.authentication.dto.RegistrationRequest;
import ch.coll.ctf.adapter.api.rest.authentication.mapper.RegistrationRequestMapper;
import ch.coll.ctf.domain.authentication.port.in.AuthenticationServicePort;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationServicePort authenticationService;

  private final RegistrationRequestMapper registrationMapper;

  @PostMapping("/login")
  public String login(@RequestBody AuthenticationRequest authenticationRequest) {
    return authenticationService.login(authenticationRequest.getUsername(), authenticationRequest.getPassword());
  }

  @PostMapping("/register")
  public String register(@RequestBody RegistrationRequest user) {
    return authenticationService.register(registrationMapper.mapRequestToUser(user));
  }
}
