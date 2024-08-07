package ch.coll.ctf.adapter.api.rest.ctf;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ch.coll.ctf.adapter.api.rest.ctf.assembler.CtfResponseAssembler;
import ch.coll.ctf.adapter.api.rest.ctf.dto.CtfRequest;
import ch.coll.ctf.adapter.api.rest.ctf.dto.CtfResponse;
import ch.coll.ctf.adapter.api.rest.ctf.mapper.CtfRequestMapper;
import ch.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import ch.coll.ctf.domain.ctf.port.in.CtfServicePort;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ctf")
@RequiredArgsConstructor
public class CtfController {
  private final CtfServicePort ctfServicePort;
  private final CtfResponseAssembler ctfResponseAssembler;
  private final CtfRequestMapper ctfRequestMapper;

  @PreAuthorize("hasAuthority('CREATE_CHALLENGE')")
  @ApiResponse(responseCode = "200", description = "Ctf created successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public CtfResponse createCtf(@Valid @RequestPart("ctf") CtfRequest ctf, @RequestPart(name = "file", required = false) MultipartFile attachment) {
    return ctfResponseAssembler.toModel(ctfServicePort.createCtf(ctfRequestMapper.mapRequestToUser(ctf), attachment));
  }

  @ApiResponse(responseCode = "200", description = "Ctfs gotten successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  @Schema(name = "CtfCollection", description = "Collection of Ctfs")
  public CollectionModel<CtfResponse> getAllCtfs() {
    return ctfResponseAssembler.toCollectionModel(ctfServicePort.getAllCtfs());
  }

  @ApiResponse(responseCode = "200", description = "Ctf gotten successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CtfResponse getCtf(@PathVariable String name) {
    return ctfResponseAssembler.toModel(ctfServicePort.getCtfByName(name));
  }

  @ApiResponse(responseCode = "200", description = "Ctf gotten successfully")
  @GetMapping(path = "/download/{name}/{file}")
  public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String name, @PathVariable String file) {
    ByteArrayResource resource = ctfServicePort.downloadFile(String.format("%s/%s", name, file));

    return ResponseEntity.ok()
      .contentLength(resource.contentLength())
      .contentType(MediaType.APPLICATION_OCTET_STREAM)
      .body(resource);
  }
}
