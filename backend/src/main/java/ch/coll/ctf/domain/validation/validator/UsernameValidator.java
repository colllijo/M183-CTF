package ch.coll.ctf.domain.validation.validator;

import ch.coll.ctf.domain.user.service.UserService;
import ch.coll.ctf.domain.validation.annotation.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UsernameValidator
    implements ConstraintValidator<ValidUsername, String> {
  private final UserService userService;

  public void initialize(ValidUsername constraint) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return userService.getUserByUsername(value).isEmpty();
  }
}
