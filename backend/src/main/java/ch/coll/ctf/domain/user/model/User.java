package ch.coll.ctf.domain.user.model;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import ch.coll.ctf.domain.authorisation.model.DefaultRoles;
import ch.coll.ctf.domain.authorisation.model.Permission;
import ch.coll.ctf.domain.authorisation.model.Role;
import ch.coll.ctf.domain.ctf.model.Solve;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class User {
  private Long id;

  @NotBlank
  private String username;

  @Size(min = 12)
  @NotBlank
  private String password;

  @Email
  @NotNull
  private String email;

  @NotNull
  @Builder.Default
  private boolean active = false;

  @NotNull
  private Set<@Valid Solve> solves;

  @NotNull
  @Builder.Default
  private Set<Role> roles = Set.of(DefaultRoles.USER.getRole());

  public Set<Permission> getPermissions() {
    return roles.stream()
        .map(Role::getPermissions)
        .flatMap(Collection::stream)
        .collect(Collectors.toSet());
  }
}
