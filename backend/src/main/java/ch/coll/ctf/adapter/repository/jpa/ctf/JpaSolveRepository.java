package ch.coll.ctf.adapter.repository.jpa.ctf;

import java.util.List;

import org.springframework.stereotype.Service;

import ch.coll.ctf.adapter.repository.jpa.ctf.mapper.SolveEntityMapper;
import ch.coll.ctf.adapter.repository.jpa.ctf.service.JpaSolveEntityRepository;
import ch.coll.ctf.domain.ctf.model.Solve;
import ch.coll.ctf.domain.ctf.port.out.SolveRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaSolveRepository implements SolveRepositoryPort {
    private final JpaSolveEntityRepository solveRepository;
    private final SolveEntityMapper solveMapper;

    public List<Solve> findAll() {
        log.info("Getting all Solves");

        return solveRepository.findAll().stream().map(solveMapper::mapEntityToModel).toList();
    }


    public Solve save(Solve solve) {
        log.info("Creating Solve - Solve={}", solve);

        return solveMapper.mapEntityToModel(solveRepository.save(solveMapper.mapModelToEntity(solve)));
    }
}
