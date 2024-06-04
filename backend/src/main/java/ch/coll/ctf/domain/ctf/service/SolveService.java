package ch.coll.ctf.domain.ctf.service;

import ch.coll.ctf.domain.ctf.model.Solve;
import ch.coll.ctf.domain.ctf.port.in.SolveServicePort;
import ch.coll.ctf.domain.ctf.port.out.SolveRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SolveService implements SolveServicePort {

    private final SolveRepositoryPort solveRepositoryPort;

    @Override
    public Solve createSolve(Solve solve) {
        return solveRepositoryPort.save(solve);
    }

    @Override
    public Optional<Solve> getSolveByCtfName(String ctfName) {
        return solveRepositoryPort.findByCtfName(ctfName);
    }

    @Override
    public List<Solve> getAllSolves() {
        return solveRepositoryPort.findAll();
    }

    @Override
    public Solve updateSolve(String ctfName, Solve solve) {
        Solve existingSolve = solveRepositoryPort.findByCtfName(ctfName)
                .orElseThrow(() -> new IllegalArgumentException("Solve with CTF name " + ctfName + " does not exist"));
        // Update the fields of existingSolve with the fields of solve
        // This depends on what fields you want to allow to be updated
        return solveRepositoryPort.save(existingSolve);
    }

    @Override
    public void deleteSolve(String ctfName) {
        solveRepositoryPort.deleteByCtfName(ctfName);
    }
}