package dev.coll.ctf.adapter.api.rest.iam.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonRootName("RefreshForm")
@NoArgsConstructor
@AllArgsConstructor
public class RefreshRequest {
  @NotBlank
  private String refreshToken;
}
