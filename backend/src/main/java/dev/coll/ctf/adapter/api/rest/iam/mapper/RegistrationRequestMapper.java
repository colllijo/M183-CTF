package dev.coll.ctf.adapter.api.rest.iam.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.coll.ctf.adapter.api.rest.RestMapperConfig;
import dev.coll.ctf.adapter.api.rest.iam.dto.RegistrationRequest;
import dev.coll.ctf.domain.user.model.User;

@Mapper(config = RestMapperConfig.class)
public interface RegistrationRequestMapper {
  @Mapping(target = "id", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "solves", ignore = true)
  @Mapping(target = "roles", ignore = true)
  public User mapRequestToUser(RegistrationRequest request);
}
