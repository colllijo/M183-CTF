package ch.coll.ctf.adapter.repository.jpa.ctf.mapper;

import org.mapstruct.Mapper;

import ch.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import ch.coll.ctf.adapter.repository.jpa.ctf.entity.SolveEntity;
import ch.coll.ctf.domain.ctf.model.Solve;

@Mapper(config = EntityMapperConfig.class)
public interface SolveEntityMapper {
  public Solve mapEntityToModel(SolveEntity entity);

  public SolveEntity mapModelToEntity(Solve model);
}
