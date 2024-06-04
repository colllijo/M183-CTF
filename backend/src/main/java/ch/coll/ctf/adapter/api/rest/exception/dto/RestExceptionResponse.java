package ch.coll.ctf.adapter.api.rest.exception.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.time.Instant;
import java.util.Map;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonRootName(value = "error")
@JsonInclude(NON_NULL)
@Relation(collectionRelation = "errors")
public class RestExceptionResponse extends RepresentationModel<RestExceptionResponse> {
  private Integer status;
  private String error;
  private String message;
  private Map<String, Map<String, String>> details;
  private Instant timestamp;
}
