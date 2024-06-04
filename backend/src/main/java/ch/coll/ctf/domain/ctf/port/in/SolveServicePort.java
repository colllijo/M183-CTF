package ch.coll.ctf.domain.ctf.port.in;

import ch.coll.ctf.domain.ctf.model.Solve;

import java.util.List;
import java.util.Optional;

public interface SolveServicePort {

    Solve createSolve(Solve solve);

    Optional<Solve> getSolveByCtfName(String ctfName);

    List<Solve> getAllSolves();

    Solve updateSolve(String ctfName, Solve solve);

    void deleteSolve(String ctfName);
}