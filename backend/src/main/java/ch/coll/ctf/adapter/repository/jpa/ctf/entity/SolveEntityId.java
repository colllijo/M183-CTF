package ch.coll.ctf.adapter.repository.jpa.ctf.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class SolveEntityId implements Serializable {
  private Long ctf;
  private Long solver;
}
