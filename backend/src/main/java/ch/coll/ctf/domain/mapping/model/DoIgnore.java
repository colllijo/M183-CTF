package ch.coll.ctf.domain.mapping.model;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import org.mapstruct.Qualifier;

@Qualifier
@Target(METHOD)
@Retention(RUNTIME)
public @interface DoIgnore {
}
