package ch.coll.ctf.domain.ctf.port.out;

import ch.coll.ctf.domain.ctf.model.Solve;

import java.util.List;
import java.util.Optional;

public interface SolveRepositoryPort {

    Solve save(Solve solve);

    Optional<Solve> findByCtfName(String ctfName);

    List<Solve> findAll();

    void deleteByCtfName(String ctfName);
}