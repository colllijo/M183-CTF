package dev.coll.ctf.adapter.api.rest.user;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.coll.ctf.adapter.api.rest.user.assembler.UserResponseAssembler;
import dev.coll.ctf.adapter.api.rest.user.dto.UserInfoResponse;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserServicePort userService;

  private final UserResponseAssembler userResponseAssembler;

  @ApiResponse(responseCode = "200", description = "User is authenticated")
  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<UserInfoResponse> getUsers() {
    return userResponseAssembler.toCollectionModel(userService.getUsers());
  }
}
