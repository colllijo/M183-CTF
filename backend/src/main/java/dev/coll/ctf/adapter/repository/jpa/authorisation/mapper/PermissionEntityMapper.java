package dev.coll.ctf.adapter.repository.jpa.authorisation.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import dev.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.PermissionEntity;
import dev.coll.ctf.domain.authorisation.model.Permission;
import dev.coll.ctf.domain.mapping.model.CycleAvoidingMappingContext;
import dev.coll.ctf.domain.mapping.model.DoIgnore;

@Mapper(config = EntityMapperConfig.class, uses = { RoleEntityMapper.class })
public interface PermissionEntityMapper {
  @DoIgnore
  public default Permission mapEntityToModel(PermissionEntity entity) {
    return mapEntityToModel(entity, new CycleAvoidingMappingContext());
  }

  public Permission mapEntityToModel(PermissionEntity entity, @Context CycleAvoidingMappingContext context);

  @DoIgnore
  public default PermissionEntity mapModelToEntity(Permission model) {
    return mapModelToEntity(model, new CycleAvoidingMappingContext());
  }

  public PermissionEntity mapModelToEntity(Permission model, @Context CycleAvoidingMappingContext context);
}
