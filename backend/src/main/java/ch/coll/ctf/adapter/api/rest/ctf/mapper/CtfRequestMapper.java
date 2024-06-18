package ch.coll.ctf.adapter.api.rest.ctf.mapper;

import ch.coll.ctf.adapter.api.rest.RestMapperConfig;
import ch.coll.ctf.adapter.api.rest.authentication.dto.RegistrationRequest;
import ch.coll.ctf.domain.user.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = RestMapperConfig.class)
public interface CtfRequestMapper {
 /* @Mapping(target = "id", ignore = true)
  @Mapping(target = "role", ignore = true)
  @Mapping(target = "active", ignore = true)
  @Mapping(target = "solves", ignore = true)
  public User mapRequestToUser(RegistrationRequest request);
*/}
