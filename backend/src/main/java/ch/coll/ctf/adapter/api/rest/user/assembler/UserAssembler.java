package ch.coll.ctf.adapter.api.rest.user.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ch.coll.ctf.domain.user.model.User;

@Component
public class UserAssembler implements RepresentationModelAssembler<User, EntityModel<User>> {
  @Override
  public EntityModel<User> toModel(User entity) {
    return EntityModel.of(entity);
  }

  @Override
  public CollectionModel<EntityModel<User>> toCollectionModel(Iterable<? extends User> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
