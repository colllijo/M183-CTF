package ch.coll.ctf.adapter.repository.jpa.ctf.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ch.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import ch.coll.ctf.adapter.repository.jpa.ctf.entity.SolveEntity;
import ch.coll.ctf.domain.ctf.model.Solve;

@Mapper(config = EntityMapperConfig.class)
public interface SolveEntityMapper {
  public Solve mapEntityToModel(SolveEntity entity);

  @Mapping(target = "ctf.id", ignore = true)
  @Mapping(target = "solver.id", ignore = true)
  public SolveEntity mapModelToEntity(Solve model);
}
