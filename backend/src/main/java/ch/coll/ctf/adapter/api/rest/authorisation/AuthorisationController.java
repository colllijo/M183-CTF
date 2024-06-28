package ch.coll.ctf.adapter.api.rest.authorisation;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.coll.ctf.adapter.api.rest.authorisation.assembler.PermissionResponseAssembler;
import ch.coll.ctf.adapter.api.rest.authorisation.assembler.RoleResponseAssembler;
import ch.coll.ctf.adapter.api.rest.authorisation.dto.PermissionResponse;
import ch.coll.ctf.adapter.api.rest.authorisation.dto.RoleRequest;
import ch.coll.ctf.adapter.api.rest.authorisation.dto.RoleResponse;
import ch.coll.ctf.adapter.api.rest.user.assembler.UserInfoResponseAssembler;
import ch.coll.ctf.adapter.api.rest.user.dto.UserDetailsResponse;
import ch.coll.ctf.domain.authorisation.port.in.AuthorisationServicePort;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/authorisation")
@RequiredArgsConstructor
public class AuthorisationController {
  private final AuthorisationServicePort authorisationService;

  private final UserInfoResponseAssembler userInfoAssembler;
  private final RoleResponseAssembler roleResponseAssembler;
  private final PermissionResponseAssembler permissionResponseAssembler;

  @PreAuthorize("hasAuthority('READ_ROLES')")
  @ApiResponse(responseCode = "200", description = "List of all roles")
  @GetMapping(path = "/roles", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<RoleResponse> getRoles() {
    return roleResponseAssembler.toCollectionModel(authorisationService.getRoles());
  }

  @PreAuthorize("hasAuthority('MODIFY_USER_ROLES')")
  @ApiResponse(responseCode = "200", description = "Add role to user")
  @PutMapping(path = "/roles/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDetailsResponse addRoleToUser(@PathVariable String username, @Valid @RequestBody RoleRequest role) {
    return userInfoAssembler.toModel(authorisationService.addRoleToUser(username, role.getName()));
  }

  @PreAuthorize("hasAuthority('MODIFY_USER_ROLES')")
  @ApiResponse(responseCode = "200", description = "Add role to user")
  @DeleteMapping(path = "/roles/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
  public UserDetailsResponse removeRoleFromUser(@PathVariable String username, @Valid @RequestBody RoleRequest role) {
    return userInfoAssembler.toModel(authorisationService.removeRoleFromUser(username, role.getName()));
  }

  @PreAuthorize("hasAuthority('READ_PERMISSIONS')")
  @ApiResponse(responseCode = "200", description = "List of all permissions")
  @GetMapping(path = "/permissions", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<PermissionResponse> getPermissions() {
    return permissionResponseAssembler.toCollectionModel(authorisationService.getPermissions());
  }
}
