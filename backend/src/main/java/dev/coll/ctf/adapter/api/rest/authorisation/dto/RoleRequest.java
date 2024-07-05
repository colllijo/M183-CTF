package dev.coll.ctf.adapter.api.rest.authorisation.dto;

import com.fasterxml.jackson.annotation.JsonRootName;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@JsonRootName("RoleForm")
@AllArgsConstructor
@NoArgsConstructor
public class RoleRequest {
  @NotBlank
  private String name;
  private String description;
}
