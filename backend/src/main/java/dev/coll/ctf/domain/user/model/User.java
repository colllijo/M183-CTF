package dev.coll.ctf.domain.user.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import dev.coll.ctf.domain.ctf.model.Solve;
import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.iam.model.authorisation.Roles;
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
  private boolean active;
  @Builder.Default
  private Set<Solve> solves = new HashSet<>();
  @Builder.Default
  private Set<Role> roles = new HashSet<>(Set.of(Roles.USER.getRole()));

  public Set<Permission> getPermissions() {
    return roles.stream()
        .map(Role::getPermissions)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }
}
