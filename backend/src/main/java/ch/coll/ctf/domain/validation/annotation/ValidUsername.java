package ch.coll.ctf.domain.validation.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import ch.coll.ctf.domain.validation.validator.UsernameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target(FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = { UsernameValidator.class })
public @interface ValidUsername {
  public String message() default "Username must be unique";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
