package dev.coll.ctf.adapter.api.rest.ctf;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.coll.ctf.adapter.api.rest.ctf.assembler.CtfResponseAssembler;
import dev.coll.ctf.adapter.api.rest.ctf.assembler.SolveResponseAssembler;
import dev.coll.ctf.adapter.api.rest.ctf.dto.CtfRequest;
import dev.coll.ctf.adapter.api.rest.ctf.dto.CtfResponse;
import dev.coll.ctf.adapter.api.rest.ctf.dto.CtfSolveRequest;
import dev.coll.ctf.adapter.api.rest.ctf.dto.SolveResponse;
import dev.coll.ctf.adapter.api.rest.ctf.mapper.CtfRequestMapper;
import dev.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import dev.coll.ctf.adapter.api.rest.response.ApiBadRequestResponse;
import dev.coll.ctf.domain.ctf.model.exception.CtfNotFoundException;
import dev.coll.ctf.domain.ctf.port.in.CtfServicePort;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ctf")
@RequiredArgsConstructor
public class CtfController {
  private final CtfServicePort ctfService;
  private final CtfResponseAssembler ctfResponseAssembler;
  private final SolveResponseAssembler solveResponseAssembler;
  private final CtfRequestMapper ctfRequestMapper;

  @ApiResponse(responseCode = "200", description = "Ctfs gotten successfully")
  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<CtfResponse> getCtfs() {
    return ctfResponseAssembler.toCollectionModel(ctfService.getAllCtfs());
  }

  @ApiResponse(responseCode = "200", description = "Ctf gotten successfully")
  @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CtfResponse getCtf(@PathVariable String name) {
    return ctfResponseAssembler.toModel(ctfService.getCtfByName(name).orElseThrow(() -> new CtfNotFoundException(name)));
  }

  @ApiResponse(responseCode = "200", description = "Ctf created successfully")
  @ApiBadRequestResponse
  @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public CtfResponse createCtf(@Valid @RequestPart("ctf") CtfRequest ctf, @RequestPart(name = "file", required = false) MultipartFile attachment) {
    return ctfResponseAssembler.toModel(ctfService.createCtf(ctfRequestMapper.mapRequestToUser(ctf), attachment));
  }

  @ApiResponse(responseCode = "200", description = "Ctf attachment downloaded successfully")
  @ApiBadRequestResponse
  @GetMapping(path = "/download/{name}/{file}")
  public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String name, @PathVariable String file) {
    ByteArrayResource resource = ctfService.downloadFile(String.format("%s/%s", name, file));

    return ResponseEntity.ok()
      .contentLength(resource.contentLength())
      .contentType(MediaType.APPLICATION_OCTET_STREAM)
      .body(resource);
  }

  @ApiResponse(responseCode = "200", description = "Ctf created successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PostMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public SolveResponse solveCtf(@PathVariable String name, @RequestBody @Valid CtfSolveRequest solve) {
    return solveResponseAssembler.toModel(ctfService.submitFlag(name, solve.getFlag()));
  }
}
