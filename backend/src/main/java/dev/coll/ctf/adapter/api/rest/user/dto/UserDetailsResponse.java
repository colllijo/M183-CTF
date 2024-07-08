package dev.coll.ctf.adapter.api.rest.user.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.Set;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import dev.coll.ctf.adapter.api.rest.iam.dto.RoleResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
@JsonRootName(value = "UserDetails")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "UserDetailsCollection")
public class UserDetailsResponse extends RepresentationModel<UserDetailsResponse> {
  private String username;
  private String email;
  private Set<RoleResponse> roles;
}
