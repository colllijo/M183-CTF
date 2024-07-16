package dev.coll.ctf.domain.validation.model;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import dev.coll.ctf.domain.validation.service.UsernameValidator;
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
