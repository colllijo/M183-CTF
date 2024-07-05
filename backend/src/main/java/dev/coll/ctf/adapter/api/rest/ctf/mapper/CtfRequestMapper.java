package dev.coll.ctf.adapter.api.rest.ctf.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.coll.ctf.adapter.api.rest.RestMapperConfig;
import dev.coll.ctf.adapter.api.rest.ctf.dto.CtfRequest;
import dev.coll.ctf.domain.ctf.model.Ctf;

@Mapper(config = RestMapperConfig.class)
public interface CtfRequestMapper {
  @Mapping(target = "author", ignore = true)
  @Mapping(target = "solves", ignore = true)
  @Mapping(target = "filePath", ignore = true)
  public Ctf mapRequestToUser(CtfRequest request);
}
