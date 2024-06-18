package ch.coll.ctf.adapter.repository.jpa.ctf;

import ch.coll.ctf.adapter.repository.jpa.ctf.mapper.SolveEntityMapper;
import ch.coll.ctf.adapter.repository.jpa.ctf.service.JpaSolveEntityRepository;
import ch.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import ch.coll.ctf.domain.ctf.model.Solve;
import ch.coll.ctf.domain.ctf.port.out.SolveRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public Optional<Solve> findByUsername(String name) {
        log.info("Getting Solve by id - id={}", name);
return null;
     //   return solveRepository.findByUser(new UserEntity(), new Ct).map(solveMapper::mapEntityToModel);
    }

    public Solve save(Solve solve) {
        log.info("Creating Solve - Solve={}", solve);

        return solveMapper.mapEntityToModel(solveRepository.save(solveMapper.mapModelToEntity(solve)));
    }
}
