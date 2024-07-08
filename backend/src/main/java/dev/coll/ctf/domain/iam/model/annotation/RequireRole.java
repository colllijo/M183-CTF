package dev.coll.ctf.domain.iam.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.security.access.prepost.PreAuthorize;

import dev.coll.ctf.domain.iam.model.authorisation.Roles;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@PreAuthorize("hasAuthority('#{value.getAuthorityName()}')")
public @interface RequireRole {
  Roles value();
}
