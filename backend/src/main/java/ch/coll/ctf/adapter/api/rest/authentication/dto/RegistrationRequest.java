
package ch.coll.ctf.adapter.api.rest.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequest {
  private String username;
  private String password;
  private String passwordConfirmation;
  private String email;
}
