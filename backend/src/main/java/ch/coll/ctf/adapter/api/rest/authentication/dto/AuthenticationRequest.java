package ch.coll.ctf.adapter.api.rest.authentication.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
  @NotBlank
  private String username;

  @Size(min = 12)
  @NotBlank
  private String password;
}
