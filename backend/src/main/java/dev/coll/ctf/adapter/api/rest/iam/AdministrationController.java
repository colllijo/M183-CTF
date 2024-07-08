package dev.coll.ctf.adapter.api.rest.iam;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.coll.ctf.adapter.api.rest.iam.assembler.PermissionResponseAssembler;
import dev.coll.ctf.adapter.api.rest.iam.assembler.RoleResponseAssembler;
import dev.coll.ctf.adapter.api.rest.iam.dto.PermissionResponse;
import dev.coll.ctf.adapter.api.rest.iam.dto.RoleRequest;
import dev.coll.ctf.adapter.api.rest.iam.dto.RoleResponse;
import dev.coll.ctf.adapter.api.rest.user.assembler.UserDetailsResponseAssembler;
import dev.coll.ctf.adapter.api.rest.user.dto.UserDetailsResponse;
import dev.coll.ctf.domain.iam.port.in.AdministrationServicePort;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/administration")
@RequiredArgsConstructor
public class AdministrationController {
  private final AdministrationServicePort administrationService;

  private final UserDetailsResponseAssembler userInfoAssembler;
  private final RoleResponseAssembler roleResponseAssembler;
  private final PermissionResponseAssembler permissionResponseAssembler;

  @ApiResponse(responseCode = "200", description = "List of all users")
  @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<UserDetailsResponse> getUsers() {
    return userInfoAssembler.toCollectionModel(administrationService.getUsers());
  }

  @ApiResponse(responseCode = "200", description = "List of all roles")
  @GetMapping(path = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<RoleResponse> getRoles() {
    return roleResponseAssembler.toCollectionModel(administrationService.getRoles());
  }

  @ApiResponse(responseCode = "200", description = "Add role to user")
  @PutMapping(path = "/roles/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDetailsResponse addRoleToUser(@PathVariable String username, @Valid @RequestBody RoleRequest role) {
    return userInfoAssembler.toModel(administrationService.addRoleToUser(username, role.getName()));
  }

  @ApiResponse(responseCode = "200", description = "Add role to user")
  @DeleteMapping(path = "/roles/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDetailsResponse removeRoleFromUser(@PathVariable String username, @Valid @RequestBody RoleRequest role) {
    return userInfoAssembler.toModel(administrationService.removeRoleFromUser(username, role.getName()));
  }

  @ApiResponse(responseCode = "200", description = "List of all permissions")
  @GetMapping(path = "/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<PermissionResponse> getPermissions() {
    return permissionResponseAssembler.toCollectionModel(administrationService.getPermissions());
  }
}
