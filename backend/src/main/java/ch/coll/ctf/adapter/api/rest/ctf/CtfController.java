package ch.coll.ctf.adapter.api.rest.ctf;

import java.util.List;

import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.coll.ctf.adapter.api.rest.ctf.assembler.CtfResponseAssembler;
import ch.coll.ctf.adapter.api.rest.ctf.dto.CtfResponse;
import ch.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import ch.coll.ctf.domain.ctf.model.Ctf;
import ch.coll.ctf.domain.ctf.port.in.CtfServicePort;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ctf")
@RequiredArgsConstructor
public class CtfController {
  private final CtfServicePort ctfServicePort;

  private final CtfResponseAssembler ctfResponseAssembler;

  @ApiResponse(responseCode = "200", description = "Ctf created successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public CtfResponse createCtf(@RequestBody Ctf ctf) {
    Ctf createdCtf = ctfServicePort.createCtf(ctf);

    return ctfResponseAssembler.toModel(createdCtf);
  }

  @ApiResponse(responseCode = "200", description = "Ctfs gotten successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @GetMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
  public CollectionModel<CtfResponse> getAllCtfs() {
    List<Ctf> ctfs = ctfServicePort.getAllCtfs();

    System.out.println(ctfs.size());

    return ctfResponseAssembler.toCollectionModel(ctfs);
  }

  @ApiResponse(responseCode = "200", description = "Ctf gotten successfully")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
  @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
  public CtfResponse getCtf(@PathVariable String name) {
    Ctf ctf = ctfServicePort.getCtfByName(name);

    return ctfResponseAssembler.toModel(ctf);
  }
}
