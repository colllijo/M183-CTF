package dev.coll.ctf.adapter.repository.jpa.user.mapper;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import dev.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.RoleEntity;
import dev.coll.ctf.adapter.repository.jpa.authorisation.service.JpaRoleEntityRepository;
import dev.coll.ctf.adapter.repository.jpa.ctf.mapper.SolveEntityMapper;
import dev.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import dev.coll.ctf.domain.authorisation.model.Role;
import dev.coll.ctf.domain.mapping.model.CycleAvoidingMappingContext;
import dev.coll.ctf.domain.mapping.model.DoIgnore;
import dev.coll.ctf.domain.user.model.User;

@Mapper(config = EntityMapperConfig.class, uses = { SolveEntityMapper.class })
public abstract class UserEntityMapper {
  @Autowired
  protected JpaRoleEntityRepository roleRepository;

  @DoIgnore
  public User mapEntityToModel(UserEntity entity) {
    return mapEntityToModel(entity, new CycleAvoidingMappingContext());
  }

  public abstract User mapEntityToModel(UserEntity entity, @Context CycleAvoidingMappingContext context);

  @DoIgnore
  public UserEntity mapModelToEntity(User model) {
    return mapModelToEntity(model, new CycleAvoidingMappingContext());
  }

  @Mapping(source = "roles", target = "roles", qualifiedByName = "mapRolesToEntity")
  public abstract UserEntity mapModelToEntity(User model, @Context CycleAvoidingMappingContext context);

  @Named("mapRolesToEntity")
  protected Set<RoleEntity> mapRolesToEntity(Set<Role> model) {
    return model.stream()
        .map(Role::getName)
        .map(roleRepository::findByName)
        .filter(Optional::isPresent)
        .map(Optional::get)
        .collect(Collectors.toSet());
  }
}
