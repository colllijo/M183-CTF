package dev.coll.ctf.domain.ctf.model;

import java.time.Instant;

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
public class Solve {
  private Ctf ctf;
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private User solver;
  private Integer points;
  @Builder.Default
  private Instant timestamp = Instant.now();
  private Integer rank;
}
