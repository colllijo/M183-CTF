package ch.coll.ctf.adapter.repository.jpa.user.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ch.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import ch.coll.ctf.adapter.repository.jpa.ctf.mapper.SolveEntityMapper;
import ch.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import ch.coll.ctf.domain.user.model.User;

@Mapper(config = EntityMapperConfig.class, uses = { SolveEntityMapper.class })
public interface UserEntityMapper {
  public User mapEntityToModel(UserEntity entity);

  @Mapping(target = "id", ignore = true)
  public UserEntity mapModelToEntity(User model);
}
