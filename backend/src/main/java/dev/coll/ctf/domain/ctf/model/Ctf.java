package dev.coll.ctf.domain.ctf.model;

import java.util.HashSet;
import java.util.Set;

import dev.coll.ctf.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Builder
@AllArgsConstructor
public class Ctf {
  private String name;
  private String description;
  private User author;
  private String flag;
  private String filePath;
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @Builder.Default
  private Set<Solve> solves = new HashSet<>();
}

