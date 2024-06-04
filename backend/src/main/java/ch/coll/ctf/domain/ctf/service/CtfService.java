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
    public Ctf getCtf(String name) {
        return ctfRepositoryPort.findByName(name).orElseThrow(() -> new IllegalArgumentException("Ctf with name " + name + " not found"));
    }

    @Override
    public Ctf createCtf(Ctf ctf) {
        return ctfRepositoryPort.save(ctf);
    }

    @Override
    public Ctf updateCtf(String name, Ctf ctf) {
        if (ctfRepositoryPort.findByName(name).isEmpty()) {
            throw new IllegalArgumentException("Ctf with name " + name + " does not exist");
        }
        return ctfRepositoryPort.save(ctf);
    }

    @Override
    public void deleteCtf(String name) {
        ctfRepositoryPort.deleteByName(name);
    }
}