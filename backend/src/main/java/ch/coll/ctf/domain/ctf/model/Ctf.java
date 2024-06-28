package ch.coll.ctf.domain.ctf.model;

import java.util.Set;

import ch.coll.ctf.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Ctf {
  private String name;
  private String description;
  private User author;
  private String flag;
  private String filePath;
  private Set<Solve> solves;
}
