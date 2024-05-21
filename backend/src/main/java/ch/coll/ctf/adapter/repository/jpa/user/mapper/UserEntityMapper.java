package ch.coll.ctf.adapter.repository.jpa.user.mapper;

import java.util.stream.Collectors;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ch.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import ch.coll.ctf.adapter.repository.jpa.ctf.entity.CaptureTheFlagEntity;
import ch.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import ch.coll.ctf.domain.ctf.model.CaptureTheFlag;
import ch.coll.ctf.domain.user.model.User;

@Mapper(config = EntityMapperConfig.class)
public interface UserEntityMapper {
  public User mapEntityToModel(UserEntity entity);

  @AfterMapping
  default public void copySolvedChallengesToModel(User user, @MappingTarget UserEntity userEntity) {
    user.getSolvedChallenges().addAll(userEntity.getSolvedChallenges().stream()
        .map(this::mapCaptureTheFlagEntityToModel)
        .collect(Collectors.toList()));
  }

  public CaptureTheFlag mapCaptureTheFlagEntityToModel(CaptureTheFlagEntity entity);

  @Mapping(target = "id", ignore = true)
  public UserEntity mapModelToEntity(User model);

  @Mapping(target = "id", ignore = true)
  public CaptureTheFlagEntity mapCaptureTheFlagModelToEntity(CaptureTheFlag entity);
}
