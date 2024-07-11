package dev.coll.ctf.adapter.repository.jpa.ctf;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.coll.ctf.adapter.repository.jpa.ctf.entity.CtfEntity;
import dev.coll.ctf.adapter.repository.jpa.ctf.entity.SolveEntity;
import dev.coll.ctf.adapter.repository.jpa.ctf.mapper.CtfEntityMapper;
import dev.coll.ctf.adapter.repository.jpa.ctf.mapper.SolveEntityMapper;
import dev.coll.ctf.adapter.repository.jpa.ctf.service.JpaCtfEntityRepository;
import dev.coll.ctf.adapter.repository.jpa.ctf.service.JpaSolveEntityRepository;
import dev.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import dev.coll.ctf.adapter.repository.jpa.user.service.JpaUserEntityRepository;
import dev.coll.ctf.domain.ctf.model.Ctf;
import dev.coll.ctf.domain.ctf.model.Solve;
import dev.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaCtfRepository implements CtfRepositoryPort {
  private final JpaUserEntityRepository userRepository;
  private final JpaCtfEntityRepository ctfRepository;
  private final JpaSolveEntityRepository solveRepository;
  private final CtfEntityMapper ctfMapper;
  private final SolveEntityMapper solveMapper;

  @Override
  public List<Ctf> getCtfs() {
    return ctfRepository.findAll().stream().map(ctfMapper::mapEntityToModel).toList();
  }

  @Override
  public Optional<Ctf> getCtfByName(String name) {
    return ctfRepository.findByName(name).map(ctfMapper::mapEntityToModel);
  }

  @Override
  public Ctf createCtf(Ctf ctf) {
    return ctfMapper.mapEntityToModel(ctfRepository.save(ctfMapper.mapModelToEntity(ctf)));
  }

  @Override
  public Ctf updateCtf(Ctf ctf) {
    return ctfMapper.mapEntityToModel(ctfRepository.save(ctfMapper.mapModelToEntity(ctf)));
  }

  @Override
  public void deleteCtfByName(String name) {
    ctfRepository.deleteByName(name);
  }

  @Override
  public Solve createSolve(Solve solve) {
    CtfEntity ctfEntity = ctfRepository.findByName(solve.getCtf().getName()).orElseThrow(() -> new RuntimeException("Ctf not found"));
    UserEntity userEntity = userRepository.findByUsername(solve.getSolver().getUsername()).orElseThrow(() -> new RuntimeException("User not found"));

    SolveEntity entity = solveMapper.mapModelToEntity(solve);
    entity.setCtf(ctfEntity);
    entity.setSolver(userEntity);

    return solveMapper.mapEntityToModel(solveRepository.save(entity));
  }
}
