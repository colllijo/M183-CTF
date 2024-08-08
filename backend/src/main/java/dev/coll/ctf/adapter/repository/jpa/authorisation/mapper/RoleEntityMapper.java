package dev.coll.ctf.adapter.repository.jpa.authorisation.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import dev.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.RoleEntity;
import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.mapping.model.CycleAvoidingMappingContext;
import dev.coll.ctf.domain.mapping.model.DoIgnore;

@Mapper(config = EntityMapperConfig.class, uses = { PermissionEntityMapper.class })
public interface RoleEntityMapper {
  @DoIgnore
  public default Role mapEntityToModel(RoleEntity entity) {
    return mapEntityToModel(entity, new CycleAvoidingMappingContext());
  }

  public Role mapEntityToModel(RoleEntity entity, @Context CycleAvoidingMappingContext context);

  @DoIgnore
  public default RoleEntity mapModelToEntity(Role model) {
    return mapModelToEntity(model, new CycleAvoidingMappingContext());
  }

  public RoleEntity mapModelToEntity(Role model, @Context CycleAvoidingMappingContext context);
}
