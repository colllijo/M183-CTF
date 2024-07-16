package dev.coll.ctf.domain.validation.service;

import dev.coll.ctf.domain.user.port.in.UserServicePort;
import dev.coll.ctf.domain.validation.model.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsernameValidator
    implements ConstraintValidator<ValidUsername, String> {
  private final UserServicePort userService;

  public void initialize(ValidUsername constraint) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return userService.getUserByUsername(value).isEmpty();
  }
}
