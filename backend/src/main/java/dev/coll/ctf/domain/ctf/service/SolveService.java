package dev.coll.ctf.domain.ctf.service;

import dev.coll.ctf.domain.ctf.model.Solve;
import dev.coll.ctf.domain.ctf.port.in.SolveServicePort;
import dev.coll.ctf.domain.ctf.port.out.SolveRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class SolveService implements SolveServicePort {
  private final SolveRepositoryPort solveRepositoryPort;

  @Override
  public Solve createSolve(Solve solve) {
    return solveRepositoryPort.save(solve);
  }

  @Override
  public List<Solve> getSolveByUsername(String username) {
    return null;
    // return solveRepositoryPort.findByUsername(username);
  }

  @Override
  public List<Solve> getAllSolves() {
    return solveRepositoryPort.findAll();
  }
}
