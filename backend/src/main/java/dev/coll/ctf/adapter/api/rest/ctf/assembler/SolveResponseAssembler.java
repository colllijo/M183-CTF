package dev.coll.ctf.adapter.api.rest.ctf.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import dev.coll.ctf.adapter.api.rest.ctf.dto.SolveResponse;
import dev.coll.ctf.domain.ctf.model.Solve;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SolveResponseAssembler implements RepresentationModelAssembler<Solve, SolveResponse> {
  @Override
  public SolveResponse toModel(Solve entity) {
    return SolveResponse.builder()
        .points(entity.getPoints())
        .timestamp(entity.getTimestamp())
        .rank(entity.getRank())
        .build();
  }

  @Override
  public CollectionModel<SolveResponse> toCollectionModel(Iterable<? extends Solve> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
