package dev.coll.ctf.domain.iam.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;

class AuthenticationProviderTest {
  private AuthenticationProvider testee;

  private UserServicePort mockUserService;
  private PasswordEncoder mockPasswordEncoder;

  @BeforeEach
  public void setup() {
    mockUserService = mock(UserServicePort.class);
    mockPasswordEncoder = mock(PasswordEncoder.class);

    testee = new AuthenticationProvider(mockUserService, mockPasswordEncoder);
  }

  @Test
  void authenticateShouldReturnAuthentication() {
    // Given
    String username = "username";
    String password = "password";
    User user = User.builder().username(username).password(password).build();
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);

    // When
    when(mockUserService.getUserByUsername(username)).thenReturn(Optional.of(user));
    when(mockPasswordEncoder.matches(password, user.getPassword())).thenReturn(true);

    Authentication result = testee.authenticate(authentication);

    // Then
    assertThat(result).isInstanceOf(UsernamePasswordAuthenticationToken.class).extracting("principal").isEqualTo(user);
    verify(mockUserService, times(1)).getUserByUsername(username);
    verify(mockPasswordEncoder, times(1)).matches(password, user.getPassword());
  }

  @Test
  void authenticateWithWrongPasswordShouldThrowException() {
    // Given
    String username = "username";
    String password = "password";
    String wrongPassword = "wrongPassword";
    User user = User.builder().username(username).password(password).build();
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, wrongPassword);

    // When
    when(mockUserService.getUserByUsername(username)).thenReturn(Optional.of(user));
    when(mockPasswordEncoder.matches(wrongPassword, user.getPassword())).thenReturn(false);

    // Then
    assertThatThrownBy(() -> testee.authenticate(authentication)).isInstanceOf(BadCredentialsException.class).hasMessage("Invalid credentials");
    verify(mockUserService, times(1)).getUserByUsername(username);
    verify(mockPasswordEncoder, times(1)).matches(wrongPassword, user.getPassword());
  }

  @Test
  void authenticateWithUnknownUserShouldThrowException() {
    // Given
    String username = "username";
    String password = "password";
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);

    // When
    when(mockUserService.getUserByUsername(username)).thenReturn(Optional.empty());
    when(mockPasswordEncoder.matches(eq(password), anyString())).thenReturn(false);

    // Then
    assertThatThrownBy(() -> testee.authenticate(authentication)).isInstanceOf(BadCredentialsException.class).hasMessage("Invalid credentials");
    verify(mockUserService, times(1)).getUserByUsername(username);
    verify(mockPasswordEncoder, times(1)).matches(eq(password), anyString());
  }

  @Test
  void supportsShouldReturnTrueForUsernamePasswordAuthenticationToken() {
    // Given
    Class<?> authentication = UsernamePasswordAuthenticationToken.class;

    // When
    boolean result = testee.supports(authentication);

    // Then
    assertThat(result).isTrue();
  }
}
