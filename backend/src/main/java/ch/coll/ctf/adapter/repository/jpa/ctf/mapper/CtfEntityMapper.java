package ch.coll.ctf.adapter.repository.jpa.ctf.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ch.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import ch.coll.ctf.adapter.repository.jpa.ctf.entity.CtfEntity;
import ch.coll.ctf.adapter.repository.jpa.user.mapper.UserEntityMapper;
import ch.coll.ctf.domain.ctf.model.Ctf;
import ch.coll.ctf.domain.mapping.model.CycleAvoidingMappingContext;
import ch.coll.ctf.domain.mapping.model.DoIgnore;

@Mapper(config = EntityMapperConfig.class, uses = {SolveEntityMapper.class, UserEntityMapper.class})
public interface CtfEntityMapper {
  @DoIgnore
  public default Ctf mapEntityToModel(CtfEntity entity) {
    return mapEntityToModel(entity, new CycleAvoidingMappingContext());
  }

  public Ctf mapEntityToModel(CtfEntity entity, @Context CycleAvoidingMappingContext context);

  @DoIgnore
  public default CtfEntity mapModelToEntity(Ctf model) {
    return mapModelToEntity(model, new CycleAvoidingMappingContext());
  }

  public CtfEntity mapModelToEntity(Ctf model, @Context CycleAvoidingMappingContext context);
}
