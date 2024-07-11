package dev.coll.ctf.adapter.api.rest.ctf.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonRootName("CtfSolveForm")
@AllArgsConstructor
@NoArgsConstructor
public class CtfSolveRequest {
  @Pattern(regexp = "^CCTF\\{[a-zA-Z0-9_-]+}$", message = "Flag must be in the format CCTF{<FLAG>}")
  private String flag;
}
