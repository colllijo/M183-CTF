package dev.coll.ctf.adapter.api.rest.iam.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import dev.coll.ctf.adapter.api.rest.iam.dto.PermissionResponse;
import dev.coll.ctf.domain.iam.model.authorisation.Permission;

@Component
public class PermissionResponseAssembler implements RepresentationModelAssembler<Permission, PermissionResponse> {
  @Override
  public PermissionResponse toModel(Permission entity) {
    return PermissionResponse.builder()
        .name(entity.getName())
        .description(entity.getDescription())
        .build();
  }

  @Override
  public CollectionModel<PermissionResponse> toCollectionModel(Iterable<? extends Permission> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
