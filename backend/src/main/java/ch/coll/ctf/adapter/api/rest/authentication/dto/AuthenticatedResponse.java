package ch.coll.ctf.adapter.api.rest.authentication.dto;

import org.springframework.hateoas.RepresentationModel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AuthenticatedResponse extends RepresentationModel<AuthenticatedResponse> {
  private String accessToken;
}
