package dev.coll.ctf.adapter.api.rest.ctf.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import dev.coll.ctf.adapter.api.rest.user.dto.UserInfoResponse;
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
@JsonRootName("Ctf")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "ctfCollection")
public class CtfResponse extends RepresentationModel<CtfResponse> {
  private String name;
  private String description;
  private UserInfoResponse author;
  private String filePath;
}
