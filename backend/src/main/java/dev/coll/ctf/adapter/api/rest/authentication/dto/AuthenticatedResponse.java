package dev.coll.ctf.adapter.api.rest.authentication.dto;

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
@JsonRootName("Authentication")
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class AuthenticatedResponse extends RepresentationModel<AuthenticatedResponse> {
  private String username;
  private Tokens tokens;

  @Data
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonInclude(NON_NULL)
  @EqualsAndHashCode
  public static class Tokens {
    private String accessToken;
    private String refreshToken;
  }
}
