
package ch.coll.ctf.adapter.api.rest.authentication.dto;

import ch.coll.ctf.domain.validation.annotation.ValidPasswordConfirmation;
import ch.coll.ctf.domain.validation.annotation.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ValidPasswordConfirmation
public class RegistrationRequest {
  @NotBlank
  @ValidUsername
  private String username;

  @Size(min = 12)
  @NotBlank
  private String password;

  @Size(min = 12)
  @NotBlank
  private String passwordConfirmation;

  @Email
  @NotNull
  private String email;
}
