package dev.coll.ctf.adapter.api.rest.user;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.coll.ctf.adapter.api.rest.user.assembler.UserInfoResponseAssembler;
import dev.coll.ctf.adapter.api.rest.user.assembler.UserResponseAssembler;
import dev.coll.ctf.adapter.api.rest.user.dto.UserDetailsResponse;
import dev.coll.ctf.adapter.api.rest.user.dto.UserInfoResponse;
import dev.coll.ctf.domain.user.port.in.UserAdministrationServicePort;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserServicePort userService;
  private final UserAdministrationServicePort userAdministrationService;

  private final UserResponseAssembler userResponseAssembler;
  private final UserInfoResponseAssembler userInfoResponseAssembler;

  @ApiResponse(responseCode = "200", description = "User is authenticated")
  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<UserInfoResponse> getUsers() {
    return userResponseAssembler.toCollectionModel(userService.getUsers());
  }

  @PreAuthorize("hasAuthority('READ_USERS')")
  @ApiResponse(responseCode = "200", description = "User is authenticated")
  @GetMapping(path = "/detailed", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<UserDetailsResponse> getUsersInfo() {
    return userInfoResponseAssembler.toCollectionModel(userAdministrationService.getUsers());
  }
}
