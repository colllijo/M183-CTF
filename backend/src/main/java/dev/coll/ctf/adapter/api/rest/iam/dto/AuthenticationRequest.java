package dev.coll.ctf.adapter.api.rest.iam.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonRootName("AuthenticationForm")
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
  @NotBlank
  private String username;

  @Size(min = 12)
  @NotBlank
  private String password;
}
