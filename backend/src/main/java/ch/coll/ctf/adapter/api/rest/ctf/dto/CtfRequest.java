package ch.coll.ctf.adapter.api.rest.ctf.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import ch.coll.ctf.domain.validation.annotation.ValidPasswordConfirmation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonRootName("CtfForm")
@AllArgsConstructor
@NoArgsConstructor
@ValidPasswordConfirmation
public class CtfRequest {
  @NotBlank
  private String name;
  private String description;
  @NotBlank
  @Pattern(regexp = "^CCTF\\{a-zA-Z0-9_-}$", message = "Flag must be in the format CCTF{<FLAG>}")
  private String flag;
}
