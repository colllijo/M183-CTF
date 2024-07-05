package dev.coll.ctf.domain.ctf.port.out;

import dev.coll.ctf.domain.ctf.model.Ctf;

import java.util.List;
import java.util.Optional;

public interface CtfRepositoryPort {
  List<Ctf> findAll();

  Optional<Ctf> findByName(String name);

  Ctf save(Ctf challenge);

  void deleteByName(String name);
}
