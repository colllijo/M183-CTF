package ch.coll.ctf.domain.ctf.model;

import java.time.Instant;

import ch.coll.ctf.domain.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Solve {
  private CaptureTheFlag ctf;
  private User solver;
  private Integer points;
  private Instant timestamp;
  private Integer rank;
}
