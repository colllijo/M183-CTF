package ch.coll.ctf.domain.ctf.port.in;

import ch.coll.ctf.domain.ctf.model.Ctf;

import java.util.List;

public interface CtfServicePort {
  List<Ctf> getAllCtfs();

  Ctf getCtfByName(String name);

  Ctf createCtf(Ctf ctf);

  Ctf updateCtf(String name, Ctf ctf);

  void deleteCtf(String name);
}
