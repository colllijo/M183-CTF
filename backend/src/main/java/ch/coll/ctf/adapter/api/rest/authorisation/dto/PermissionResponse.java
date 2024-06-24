package ch.coll.ctf.adapter.api.rest.authorisation.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(NON_NULL)
@EqualsAndHashCode(callSuper = false)
public class PermissionResponse extends RepresentationModel<PermissionResponse> {
  private String name;
  private String description;
}
