package ch.coll.ctf.adapter.api.rest.user;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.coll.ctf.adapter.api.rest.user.assembler.UserAssembler;
import ch.coll.ctf.adapter.api.rest.user.assembler.UserResponseAssembler;
import ch.coll.ctf.adapter.api.rest.user.dto.UserResponse;
import ch.coll.ctf.domain.user.model.User;
import ch.coll.ctf.domain.user.port.in.UserServicePort;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserServicePort userService;

  private final UserResponseAssembler userResponseAssembler;
  private final UserAssembler userAssembler;

  @ApiResponse(responseCode = "200", description = "User is authenticated")
  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<UserResponse> getUsers() {
    return userResponseAssembler.toCollectionModel(userService.getUsers());
  }

  @PreAuthorize("hasAuthority('READ_USERS')")
  @ApiResponse(responseCode = "200", description = "User is authenticated")
  @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<EntityModel<User>> getUsersInfo() {
    return userAssembler.toCollectionModel(userService.getUsers());
  }
}
