package ch.coll.ctf.domain.ctf.port.out;

import ch.coll.ctf.domain.ctf.model.CaptureTheFlag;

import java.util.List;
import java.util.Optional;

public interface CtfRepositoryPort {
    public List<CaptureTheFlag> findAll();
    public Optional<CaptureTheFlag> findById(Long id);
    public CaptureTheFlag save(CaptureTheFlag challenge);
    public void deleteById(Long id);
}
