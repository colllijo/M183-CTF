package ch.coll.ctf.adapter.api.rest.user.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

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
@JsonRootName(value = "user")
@EqualsAndHashCode(callSuper = false)
public class UserResponse extends RepresentationModel<UserResponse> {
  private String username;

  private Integer points;

  // private Set<SolveResponse> solves;
}
