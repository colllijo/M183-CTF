
package ch.coll.ctf.adapter.api.rest.authentication.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import ch.coll.ctf.domain.validation.annotation.ValidPasswordConfirmation;
import ch.coll.ctf.domain.validation.annotation.ValidUsername;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonRootName("RegistrationForm")
@AllArgsConstructor
@NoArgsConstructor
@ValidPasswordConfirmation
public class RegistrationRequest {
  @NotBlank
  @ValidUsername
  private String username;

  @Size(min = 12)
  @NotBlank
  @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$")
  private String password;

  @Size(min = 12)
  @NotBlank
  private String passwordConfirmation;

  @Email
  @NotNull
  private String email;
}
