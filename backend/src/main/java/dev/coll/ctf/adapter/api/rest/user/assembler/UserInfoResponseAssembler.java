package dev.coll.ctf.adapter.api.rest.user.assembler;

import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import dev.coll.ctf.adapter.api.rest.authorisation.assembler.RoleResponseAssembler;
import dev.coll.ctf.adapter.api.rest.user.dto.UserDetailsResponse;
import dev.coll.ctf.domain.user.model.User;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserInfoResponseAssembler implements RepresentationModelAssembler<User, UserDetailsResponse> {
  private final RoleResponseAssembler roleResponseAssembler;

  @Override
  public UserDetailsResponse toModel(User entity) {
    return UserDetailsResponse.builder()
      .username(entity.getUsername())
      .email(entity.getEmail())
      .roles(entity.getRoles().stream().map(roleResponseAssembler::toModel).collect(Collectors.toSet()))
      .build();
  }

  @Override
  public CollectionModel<UserDetailsResponse> toCollectionModel(Iterable<? extends User> entities) {
    return RepresentationModelAssembler.super.toCollectionModel(entities);
  }
}
