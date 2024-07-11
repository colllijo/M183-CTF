package dev.coll.ctf.domain.ctf.port.out;

import java.util.List;
import java.util.Optional;

import dev.coll.ctf.domain.ctf.model.Ctf;
import dev.coll.ctf.domain.ctf.model.Solve;

public interface CtfRepositoryPort {
  List<Ctf> getCtfs();

  Optional<Ctf> getCtfByName(String name);

  Ctf createCtf(Ctf challenge);

  Ctf updateCtf(Ctf challenge);

  void deleteCtfByName(String name);

  Solve createSolve(Solve solve);
}
