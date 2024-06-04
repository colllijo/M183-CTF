package ch.coll.ctf.domain.validation.validator;

import java.util.Objects;

import ch.coll.ctf.adapter.api.rest.authentication.dto.RegistrationRequest;
import ch.coll.ctf.domain.validation.annotation.ValidPasswordConfirmation;
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
