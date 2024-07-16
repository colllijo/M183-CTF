package dev.coll.ctf.domain.iam.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import dev.coll.ctf.domain.iam.model.authentication.AuthenticationTokens;
import dev.coll.ctf.domain.iam.model.exception.InvalidRefreshTokenException;
import dev.coll.ctf.domain.iam.model.exception.UnauthenticatedException;
import dev.coll.ctf.domain.jwt.model.SecureToken;
import dev.coll.ctf.domain.jwt.port.in.JwtServicePort;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;

class AuthenticationServiceTest {
  private AuthenticationService testee;

  private AuthenticationManager mockAuthenticationManager;
  private JwtServicePort mockJwtService;
  private UserServicePort mockUserService;

  @BeforeEach
  void setup() {
    mockAuthenticationManager = mock(AuthenticationManager.class);
    mockJwtService = mock(JwtServicePort.class);
    mockUserService = mock(UserServicePort.class);

    testee = new AuthenticationService(mockAuthenticationManager, mockJwtService, mockUserService);
  }

  @Test
  void getAuthenticatedUserShouldReturnUser() {
    // Given
    User user = User.builder().username("user1").build();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, null);
    SecurityContextHolder.getContext().setAuthentication(token);

    // When
    User result = testee.getAuthenticatedUser();

    // Then
    SecurityContextHolder.getContext().setAuthentication(authentication);

    assertThat(result).isEqualTo(user);
  }

  @Test
  void getAuthenticatedUserThrowsExceptionWhenUserIsNotAuthenticated() {
    assertThatThrownBy(() -> testee.getAuthenticatedUser()).isInstanceOf(UnauthenticatedException.class);
  }

  @Test
  void registerShouldCreateNewUserAndReturnTokens() {
    // Given
    User registrant = User.builder().username("user1").build();
    User user = User.builder().username("user1").id(1L).build();

    // When
    when(mockUserService.createUser(registrant)).thenReturn(user);
    when(mockJwtService.generateSecureAccessToken(user)).thenReturn(SecureToken.builder().build());
    when(mockJwtService.generateSecureRefreshToken(user)).thenReturn(SecureToken.builder().build());

    AuthenticationTokens result = testee.register(registrant);

    // Then
    assertThat(result.accessToken()).isNotNull();
    assertThat(result.refreshToken()).isNotNull();
    verify(mockUserService, times(1)).createUser(registrant);
    verify(mockJwtService, times(1)).generateSecureAccessToken(user);
    verify(mockJwtService, times(1)).generateSecureRefreshToken(user);
  }

  @Test
  void loginShouldReturnAuthenticationTokens() {
    // Given
    User user = User.builder().username("user1").build();
    Authentication authentication = mock(Authentication.class);

    // When
    when(mockAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken("user1", "password")))
      .thenReturn(authentication);
    when(authentication.isAuthenticated()).thenReturn(true);
    when(authentication.getPrincipal()).thenReturn(user);
    when(mockJwtService.generateSecureAccessToken(user)).thenReturn(SecureToken.builder().build());
    when(mockJwtService.generateSecureRefreshToken(user)).thenReturn(SecureToken.builder().build());

    AuthenticationTokens result = testee.login("user1", "password");

    // Then
    assertThat(result.accessToken()).isNotNull();
    assertThat(result.refreshToken()).isNotNull();
    verify(mockAuthenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken("user1", "password"));
    verify(authentication, times(1)).isAuthenticated();
    verify(authentication, times(2)).getPrincipal();
    verify(mockJwtService, times(1)).generateSecureAccessToken(user);
    verify(mockJwtService, times(1)).generateSecureRefreshToken(user);
  }

  @Test
  void loginWithBadAuthenticationShouldReturnNull() {
    // Given
    Authentication authentication = mock(Authentication.class);

    // When
    when(mockAuthenticationManager.authenticate(new UsernamePasswordAuthenticationToken("user1", "password")))
      .thenReturn(authentication);
    when(authentication.isAuthenticated()).thenReturn(false);

    AuthenticationTokens result = testee.login("user1", "password");

    // Then
    assertThat(result).isNull();
    verify(mockAuthenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken("user1", "password"));
    verify(authentication, times(1)).isAuthenticated();
  }

  @Test
  void refreshShouldReturnNewAccessToken() {
    // Given
    SecureToken refreshToken = SecureToken.builder().value("refreshToken").build();
    User user = User.builder().username("user1").build();

    // When
    when(mockJwtService.extractUsername("refreshToken")).thenReturn("user1");
    when(mockUserService.getUserByUsername("user1")).thenReturn(Optional.of(user));
    when(mockJwtService.isTokenValid(refreshToken, user)).thenReturn(true);
    when(mockJwtService.generateSecureAccessToken(user)).thenReturn(SecureToken.builder().build());

    AuthenticationTokens result = testee.refresh(refreshToken);

    // Then
    assertThat(result.accessToken()).isNotNull();
    verify(mockJwtService, times(1)).extractUsername("refreshToken");
    verify(mockUserService, times(1)).getUserByUsername("user1");
    verify(mockJwtService, times(1)).isTokenValid(refreshToken, user);
    verify(mockJwtService, times(1)).generateSecureAccessToken(user);
  }

  @Test
  void refreshShouldThrowExceptionWhenRefreshTokenIsInvalid() {
    // Given
    SecureToken refreshToken = SecureToken.builder().value("refreshToken").build();
    User user = User.builder().username("user1").build();

    // When
    when(mockJwtService.extractUsername("refreshToken")).thenReturn("user1");
    when(mockUserService.getUserByUsername("user1")).thenReturn(Optional.of(user));
    when(mockJwtService.isTokenValid(refreshToken, user)).thenReturn(false);

    // Then
    assertThatThrownBy(() -> testee.refresh(refreshToken)).isInstanceOf(InvalidRefreshTokenException.class);
  }
}
