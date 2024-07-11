package dev.coll.ctf.domain.ctf.model;

import java.util.Set;

import dev.coll.ctf.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@AllArgsConstructor
public class Ctf {
  private String name;
  private String description;
  private User author;
  private String flag;
  private String filePath;
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<Solve> solves;
}
