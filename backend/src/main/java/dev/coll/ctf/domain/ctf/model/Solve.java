package dev.coll.ctf.domain.ctf.model;

import java.time.Instant;

import dev.coll.ctf.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Solve {
  private Ctf ctf;
  private User solver;
  private Integer points;
  private Instant timestamp;
  private Integer rank;
}
