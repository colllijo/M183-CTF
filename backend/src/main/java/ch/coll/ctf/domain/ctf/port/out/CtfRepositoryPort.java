package ch.coll.ctf.domain.ctf.port.out;

import ch.coll.ctf.domain.ctf.model.Ctf;

import java.util.List;
import java.util.Optional;

public interface CtfRepositoryPort {
    public List<Ctf> findAll();
    public Optional<Ctf> findById(Long id);
    public Ctf save(Ctf challenge);
    public void deleteById(Long id);
}
