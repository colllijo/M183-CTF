package ch.coll.ctf.domain.ctf.port.in;

import ch.coll.ctf.domain.ctf.model.Solve;

import java.util.List;
import java.util.Optional;

public interface SolveServicePort {

    Solve createSolve(Solve solve);

    List<Solve> getSolveByUsername(String username);

    List<Solve> getAllSolves();
}