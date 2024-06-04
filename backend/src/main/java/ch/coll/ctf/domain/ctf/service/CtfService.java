package ch.coll.ctf.domain.ctf.service;

import ch.coll.ctf.domain.ctf.model.Ctf;
import ch.coll.ctf.domain.ctf.port.in.CtfServicePort;
import ch.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CtfService implements CtfServicePort {

    private final CtfRepositoryPort ctfRepositoryPort;

    @Override
    public List<Ctf> getAllCtfs() {
        return ctfRepositoryPort.findAll();
    }

    @Override
    public Ctf getCtfById(Long id) {
        return ctfRepositoryPort.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ctf with id " + id + " not found"));
    }

    @Override
    public Ctf createCtf(Ctf ctf) {
        return ctfRepositoryPort.save(ctf);
    }

    @Override
    public Ctf updateCtf(Long id, Ctf ctf) {
        return ctfRepositoryPort.save(ctf);
    }

    @Override
    public void deleteCtf(Long id) {
        ctfRepositoryPort.deleteById(id);
    }
}