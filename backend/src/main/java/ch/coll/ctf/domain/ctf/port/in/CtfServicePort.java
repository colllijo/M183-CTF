package ch.coll.ctf.domain.ctf.port.in;

import ch.coll.ctf.domain.ctf.model.Ctf;

import java.util.List;

public interface CtfServicePort {

    public List<Ctf> getAllCtfs();

    public Ctf getCtf(String name);

    public Ctf createCtf(Ctf ctf);

    public Ctf updateCtf(String name, Ctf ctf);

    public void deleteCtf(String name);

}