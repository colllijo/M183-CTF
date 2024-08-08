package dev.coll.ctf.adapter.repository.jpa.ctf.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import dev.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import dev.coll.ctf.adapter.repository.jpa.authorisation.mapper.RoleEntityMapper;
import dev.coll.ctf.adapter.repository.jpa.ctf.entity.SolveEntity;
import dev.coll.ctf.domain.ctf.model.Solve;
import dev.coll.ctf.domain.mapping.model.CycleAvoidingMappingContext;
import dev.coll.ctf.domain.mapping.model.DoIgnore;

@Mapper(config = EntityMapperConfig.class, uses = { RoleEntityMapper.class })
public interface SolveEntityMapper {
  @DoIgnore
  public default Solve mapEntityToModel(SolveEntity entity) {
    return mapEntityToModel(entity, new CycleAvoidingMappingContext());
  }

  public Solve mapEntityToModel(SolveEntity entity, @Context CycleAvoidingMappingContext context);

  @DoIgnore
  public default SolveEntity mapModelToEntity(Solve model) {
    return mapModelToEntity(model, new CycleAvoidingMappingContext());
  }

  public SolveEntity mapModelToEntity(Solve model, @Context CycleAvoidingMappingContext context);
}
