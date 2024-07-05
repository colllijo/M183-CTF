package dev.coll.ctf.domain.authorisation.model;

import java.util.HashSet;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class Permission {
  private String name;
  private String description;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @Builder.Default
  private Set<Role> roles = new HashSet<>();
}
