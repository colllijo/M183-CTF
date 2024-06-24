package ch.coll.ctf.adapter.api.rest.user.assembler;

import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import ch.coll.ctf.adapter.api.rest.authorisation.assembler.RoleResponseAssembler;
import ch.coll.ctf.adapter.api.rest.user.dto.UserInfoResponse;
import ch.coll.ctf.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserInfoResponseAssembler implements RepresentationModelAssembler<User, UserInfoResponse> {
  private final RoleResponseAssembler roleResponseAssembler;

  @Override
  public UserInfoResponse toModel(User entity) {
    return UserInfoResponse.builder()
      .username(entity.getUsername())
      .email(entity.getEmail())
      .roles(entity.getRoles().stream().map(roleResponseAssembler::toModel).collect(Collectors.toSet()))
      .build();
  }

  @Override
  public CollectionModel<UserInfoResponse> toCollectionModel(Iterable<? extends User> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
