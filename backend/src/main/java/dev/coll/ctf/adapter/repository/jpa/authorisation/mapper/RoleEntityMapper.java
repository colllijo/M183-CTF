package dev.coll.ctf.adapter.repository.jpa.authorisation.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import dev.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.PermissionEntity;
import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.RoleEntity;
import dev.coll.ctf.adapter.repository.jpa.authorisation.service.JpaPermissionEntityRepository;
import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.mapping.model.CycleAvoidingMappingContext;
import dev.coll.ctf.domain.mapping.model.DoIgnore;

@Mapper(config = EntityMapperConfig.class)
public abstract class RoleEntityMapper {
  @Autowired
  protected JpaPermissionEntityRepository permissionRepository;

  @DoIgnore
  public Role mapEntityToModel(RoleEntity entity) {
    return mapEntityToModel(entity, new CycleAvoidingMappingContext());
  }

  public abstract Role mapEntityToModel(RoleEntity entity, @Context CycleAvoidingMappingContext context);

  @DoIgnore
  public RoleEntity mapModelToEntity(Role model) {
    return mapModelToEntity(model, new CycleAvoidingMappingContext());
  }

  @Mapping(source = "permissions", target = "permissions", qualifiedByName = "mapPermissionsToEntity")
  public abstract RoleEntity mapModelToEntity(Role model, @Context CycleAvoidingMappingContext context);

  @Named("mapPermissionsToEntity")
  protected Set<PermissionEntity> mapPermissionToEntity(Set<Permission> model) {
    return model.stream()
        .map(Permission::getName)
        .map(permissionRepository::findByName)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toSet());
  }
}
