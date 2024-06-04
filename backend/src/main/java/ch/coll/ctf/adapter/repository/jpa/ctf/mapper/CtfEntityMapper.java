package ch.coll.ctf.adapter.repository.jpa.ctf.mapper;

import ch.coll.ctf.adapter.repository.jpa.ctf.entity.CtfEntity;
import ch.coll.ctf.domain.ctf.model.Ctf;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import ch.coll.ctf.adapter.repository.jpa.EntityMapperConfig;
import ch.coll.ctf.adapter.repository.jpa.user.mapper.UserEntityMapper;

@Mapper(config = EntityMapperConfig.class, uses = {SolveEntityMapper.class, UserEntityMapper.class})
public interface CtfEntityMapper {
    public Ctf mapEntityToModel(CtfEntity entity);

    @Mapping(target = "id", ignore = true)
    public CtfEntity mapModelToEntity(Ctf model);
}
