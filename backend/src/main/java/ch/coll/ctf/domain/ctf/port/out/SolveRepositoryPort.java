package ch.coll.ctf.domain.ctf.port.out;

import ch.coll.ctf.domain.ctf.model.Solve;

import java.util.List;
import java.util.Optional;

public interface SolveRepositoryPort {

    Solve save(Solve solve);

    List<Solve> findAll();
}