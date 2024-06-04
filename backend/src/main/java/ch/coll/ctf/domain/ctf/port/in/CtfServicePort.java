package ch.coll.ctf.domain.ctf.port.in;

import ch.coll.ctf.domain.ctf.model.Ctf;

import java.util.List;

public interface CtfServicePort {

    public List<Ctf> getAllCtfs();

    public Ctf getCtfById(Long id);

    public Ctf createCtf(Ctf ctf);

    public Ctf updateCtf(Long id, Ctf ctf);

    public void deleteCtf(Long id);

}