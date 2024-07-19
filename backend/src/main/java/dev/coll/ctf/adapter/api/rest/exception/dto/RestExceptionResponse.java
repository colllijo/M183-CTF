package dev.coll.ctf.adapter.api.rest.exception.dto;

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
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "RequestError")
@JsonInclude(NON_NULL)
@Relation(collectionRelation = "requestErrorCollection")
public class RestExceptionResponse extends RepresentationModel<RestExceptionResponse> {
  private Integer status;
  private String error;
  private String message;
  private Object details;
  @Builder.Default
  private Instant timestamp = Instant.now();
}
