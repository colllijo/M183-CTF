package dev.coll.ctf.domain.iam.model.authorisation;

import java.util.List;
import java.util.stream.Stream;

import lombok.Getter;

@Getter
public enum Feature {
  ADMINISTRATION(Roles.ADMIN),
  CHALLENGE_CREATION(Roles.ADMIN, Roles.AUTHOR);

  private List<String> allowedRoles;

  private Feature(Roles... allowedRoles) {
    this.allowedRoles = Stream.of(allowedRoles).map(Roles::getAuthorityName).toList();
  }
}
