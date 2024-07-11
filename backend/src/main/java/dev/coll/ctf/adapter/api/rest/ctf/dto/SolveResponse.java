package dev.coll.ctf.adapter.api.rest.ctf.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.Instant;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

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
@JsonRootName("Solve")
@EqualsAndHashCode(callSuper = false)
@Relation(collectionRelation = "SolveCollection")
public class SolveResponse extends RepresentationModel<CtfResponse> {
  private Integer points;
  private Instant timestamp;
  private Integer rank;
}
