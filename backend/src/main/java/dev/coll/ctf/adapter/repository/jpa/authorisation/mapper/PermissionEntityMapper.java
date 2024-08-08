package dev.coll.ctf.adapter.repository.jpa.authorisation.mapper;

import org.mapstruct.Mapper;

import dev.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.PermissionEntity;
import dev.coll.ctf.domain.iam.model.authorisation.Permission;

@Mapper(config = EntityMapperConfig.class)
public interface PermissionEntityMapper {
  public default Permission mapEntityToModel(PermissionEntity entity) {
    return entity.getPermission();
  }

  public default PermissionEntity mapModelToEntity(Permission model) {
    return PermissionEntity.builder().permission(model).build();
  }
}
