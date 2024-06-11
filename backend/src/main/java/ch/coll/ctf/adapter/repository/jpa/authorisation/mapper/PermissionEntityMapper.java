package ch.coll.ctf.adapter.repository.jpa.authorisation.mapper;

import org.mapstruct.Context;
import org.mapstruct.Mapper;

import ch.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import ch.coll.ctf.adapter.repository.jpa.authorisation.entity.PermissionEntity;
import ch.coll.ctf.domain.authorisation.model.Permission;
import ch.coll.ctf.domain.mapping.model.CycleAvoidingMappingContext;
import ch.coll.ctf.domain.mapping.model.DoIgnore;

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
