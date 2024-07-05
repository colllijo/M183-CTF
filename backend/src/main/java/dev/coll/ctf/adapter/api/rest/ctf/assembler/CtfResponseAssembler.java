package dev.coll.ctf.adapter.api.rest.ctf.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import dev.coll.ctf.adapter.api.rest.ctf.dto.CtfResponse;
import dev.coll.ctf.adapter.api.rest.user.assembler.UserResponseAssembler;
import dev.coll.ctf.domain.ctf.model.Ctf;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CtfResponseAssembler implements RepresentationModelAssembler<Ctf, CtfResponse> {
  private final UserResponseAssembler userResponseAssembler;

  @Override
  public CtfResponse toModel(Ctf entity) {
    return CtfResponse.builder()
        .name(entity.getName())
        .description(entity.getDescription())
        .author(userResponseAssembler.toModel(entity.getAuthor()))
        .filePath(entity.getFilePath())
        .build();
  }

  @Override
  public CollectionModel<CtfResponse> toCollectionModel(Iterable<? extends Ctf> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
