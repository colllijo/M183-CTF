package ch.coll.ctf.adapter.api.rest.user.assembler;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ch.coll.ctf.adapter.api.rest.user.dto.UserResponse;
import ch.coll.ctf.domain.ctf.model.Solve;
import ch.coll.ctf.domain.user.model.User;

@Component
public class UserResponseAssembler implements RepresentationModelAssembler<User, UserResponse> {
  @Override
  public UserResponse toModel(User entity) {
    return UserResponse.builder()
        .username(entity.getUsername())
        .points(entity.getSolves().stream().map(Solve::getPoints).reduce(0, Integer::sum))
         // .solves(entity.getSolves().stream()
         //     .map(s -> SolveResponse.builder()
         //         .id(s.getId())
         //         .challengeId(s.getChallengeId())
         //         .timestamp(s.getTimestamp())
         //         .build())
         //    .collect(Collectors.toSet()))
        .build();
  }

  @Override
  public CollectionModel<UserResponse> toCollectionModel(Iterable<? extends User> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
