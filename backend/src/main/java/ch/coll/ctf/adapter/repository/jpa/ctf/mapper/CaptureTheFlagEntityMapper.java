package ch.coll.ctf.adapter.repository.jpa.ctf.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ch.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import ch.coll.ctf.adapter.repository.jpa.ctf.entity.CaptureTheFlagEntity;
import ch.coll.ctf.adapter.repository.jpa.user.mapper.UserEntityMapper;
import ch.coll.ctf.domain.ctf.model.CaptureTheFlag;

@Mapper(config = EntityMapperConfig.class, uses = { UserEntityMapper.class })
public interface CaptureTheFlagEntityMapper {
  public CaptureTheFlag mapEntityToModel(CaptureTheFlagEntity entity);

  @Mapping(target = "id", ignore = true)
  public CaptureTheFlagEntity mapModelToEntity(CaptureTheFlag model);
}
