package ch.coll.ctf.adapter.api.rest.authorisation.assembler;

import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ch.coll.ctf.adapter.api.rest.authorisation.dto.RoleResponse;
import ch.coll.ctf.domain.authorisation.model.Role;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RoleResponseAssembler implements RepresentationModelAssembler<Role, RoleResponse> {
  private final PermissionResponseAssembler permissionResponseAssembler;

  @Override
  public RoleResponse toModel(Role entity) {
    return RoleResponse.builder()
      .name(entity.getName())
      .description(entity.getDescription())
      .permissions(entity.getPermissions().stream().map(permissionResponseAssembler::toModel).collect(Collectors.toSet()))
      .build();
  }

  @Override
  public CollectionModel<RoleResponse> toCollectionModel(Iterable<? extends Role> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
