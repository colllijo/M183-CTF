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
    public List<Solve> getSolveByUsername(String username) {
        return solveRepositoryPort.findByUsername(username);
    }

    @Override
    public List<Solve> getAllSolves() {
        return solveRepositoryPort.findAll();
    }
}