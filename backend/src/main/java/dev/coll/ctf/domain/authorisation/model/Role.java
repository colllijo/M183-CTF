package dev.coll.ctf.domain.authorisation.model;

import java.util.HashSet;
import java.util.Set;

import dev.coll.ctf.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class Role {
  private String name;
  private String description;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @Builder.Default
  private Set<User> users = new HashSet<>();

  @Builder.Default
  private Set<Permission> permissions = new HashSet<>();
}
