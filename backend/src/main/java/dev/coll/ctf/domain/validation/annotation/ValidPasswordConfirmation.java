package dev.coll.ctf.domain.validation.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import dev.coll.ctf.domain.validation.validator.PasswordConfirmationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = { PasswordConfirmationValidator.class })
public @interface ValidPasswordConfirmation {
  public String message() default "password and password confirmation must match";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
