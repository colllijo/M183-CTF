package ch.coll.ctf.domain.ctf.port.in;

import java.util.List;

import ch.coll.ctf.domain.ctf.model.Solve;

public interface SolveServicePort {
  Solve createSolve(Solve solve);

  List<Solve> getSolveByUsername(String username);

  List<Solve> getAllSolves();
}
