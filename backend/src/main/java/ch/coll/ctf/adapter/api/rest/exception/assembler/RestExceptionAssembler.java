package ch.coll.ctf.adapter.api.rest.exception.assembler;

import java.time.Instant;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ch.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;

@Component
public class RestExceptionAssembler implements RepresentationModelAssembler<Exception, RestExceptionResponse> {
  @Override
  public RestExceptionResponse toModel(Exception entity) {
    return RestExceptionResponse.builder()
        .status(500)
        .error(entity.getClass().getSimpleName())
        .message(entity.getMessage())
        .timestamp(Instant.now())
        .build();
  }

  @Override
  public CollectionModel<RestExceptionResponse> toCollectionModel(Iterable<? extends Exception> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
