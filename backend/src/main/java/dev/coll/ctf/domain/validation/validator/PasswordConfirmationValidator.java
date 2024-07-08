package dev.coll.ctf.domain.validation.validator;

import java.util.Objects;

import dev.coll.ctf.adapter.api.rest.iam.dto.RegistrationRequest;
import dev.coll.ctf.domain.validation.annotation.ValidPasswordConfirmation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConfirmationValidator
    implements ConstraintValidator<ValidPasswordConfirmation, RegistrationRequest> {
  public void initialize(ValidPasswordConfirmation constraint) {
  }

  @Override
  public boolean isValid(RegistrationRequest value, ConstraintValidatorContext context) {
    return Objects.equals(value.getPassword(), value.getPasswordConfirmation());
  }
}
