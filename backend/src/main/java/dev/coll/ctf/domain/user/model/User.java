package dev.coll.ctf.domain.user.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import dev.coll.ctf.domain.authorisation.model.DefaultRoles;
import dev.coll.ctf.domain.authorisation.model.Permission;
import dev.coll.ctf.domain.authorisation.model.Role;
import dev.coll.ctf.domain.ctf.model.Solve;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class User {
  private Long id;
  private String username;
  private String password;
  private String email;

  @Builder.Default
  private boolean active = false;
  private Set<@Valid Solve> solves;

  @Builder.Default
  private Set<Role> roles = new HashSet<>(Set.of(DefaultRoles.USER.getRole()));

  public Set<Permission> getPermissions() {
    return roles.stream()
        .map(Role::getPermissions)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }
}
