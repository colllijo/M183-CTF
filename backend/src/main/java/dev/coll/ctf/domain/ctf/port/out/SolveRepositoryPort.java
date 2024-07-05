package dev.coll.ctf.domain.ctf.port.out;

import java.util.List;

import dev.coll.ctf.domain.ctf.model.Solve;

public interface SolveRepositoryPort {

  Solve save(Solve solve);

  List<Solve> findAll();
}
