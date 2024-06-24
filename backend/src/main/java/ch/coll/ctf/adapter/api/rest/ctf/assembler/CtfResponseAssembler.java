package ch.coll.ctf.adapter.api.rest.ctf.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ch.coll.ctf.adapter.api.rest.ctf.dto.CtfResponse;
import ch.coll.ctf.adapter.api.rest.user.assembler.UserResponseAssembler;
import ch.coll.ctf.domain.ctf.model.Ctf;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CtfResponseAssembler implements RepresentationModelAssembler<Ctf, CtfResponse> {
  private UserResponseAssembler userResponseAssembler;

  @Override
  public CtfResponse toModel(Ctf entity) {
    return CtfResponse.builder()
        .name(entity.getName())
        .description(entity.getDescription())
        .author(userResponseAssembler.toModel(entity.getAuthor()))
        .build();
  }

  @Override
  public CollectionModel<CtfResponse> toCollectionModel(Iterable<? extends Ctf> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
