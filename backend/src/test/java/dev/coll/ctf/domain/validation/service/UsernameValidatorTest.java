package dev.coll.ctf.domain.validation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;

class UsernameValidatorTest {
  private UsernameValidator testee;

  private UserServicePort mockUserService;

  @BeforeEach
  void setup() {
    mockUserService = mock(UserServicePort.class);

    testee = new UsernameValidator(mockUserService);
  }

  @Test
  void isValidShouldReturnTrueWhenUsernameIsNotTaken() {
    // Given
    String username = "user1";

    // When
    when(mockUserService.getUserByUsername(username)).thenReturn(Optional.empty());

    boolean result = testee.isValid(username, null);

    // Then
    assertThat(result).isTrue();
  }

  @Test
  void isValidShouldReturnFalseWhenUsernameIsTaken() {
    // Given
    String username = "user1";
    User user = User.builder().username(username).build();

    // When
    when(mockUserService.getUserByUsername(username)).thenReturn(Optional.of(user));

    boolean result = testee.isValid(username, null);

    // Then
    assertThat(result).isFalse();
  }
}
