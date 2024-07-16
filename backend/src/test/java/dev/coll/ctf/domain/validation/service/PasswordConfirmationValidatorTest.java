package dev.coll.ctf.domain.validation.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.coll.ctf.adapter.api.rest.iam.dto.RegistrationRequest;

class PasswordConfirmationValidatorTest {
  private PasswordConfirmationValidator testee;

  @BeforeEach
  void setup() {
    testee = new PasswordConfirmationValidator();
  }

  @Test
  void isValidShouldReturnTrueWhenPasswordsMatch() {
    // Given
    RegistrationRequest registrationRequest = RegistrationRequest.builder()
        .username("user1")
        .password("password")
        .passwordConfirmation("password")
        .build();

    // When
    boolean result = testee.isValid(registrationRequest, null);

    // Then
    assertThat(result).isTrue();
  }

  @Test
  void isValidShouldReturnFalseWhenPasswordsDoNotMatch() {
    // Given
    RegistrationRequest registrationRequest = RegistrationRequest.builder()
        .username("user1")
        .password("password")
        .passwordConfirmation("differentPassword")
        .build();

    // When
    boolean result = testee.isValid(registrationRequest, null);

    // Then
    assertThat(result).isFalse();
  }
}
