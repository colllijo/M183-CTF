package ch.coll.ctf.adapter.api.rest.ctf;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import ch.coll.ctf.adapter.api.rest.exception.dto.RestExceptionResponse;
import ch.coll.ctf.domain.ctf.model.Ctf;
import ch.coll.ctf.domain.ctf.port.in.CtfServicePort;
import ch.coll.ctf.domain.user.model.User;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ctf")
@RequiredArgsConstructor
public class CtfController {
    private final CtfServicePort ctfServicePort;

    @ApiResponse(responseCode = "200", description = "Ctf created successfully")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    @PostMapping
    public ResponseEntity<Ctf> createCtf(@RequestPart("ctf") Ctf ctf, @RequestPart("file") MultipartFile file) {
        Ctf createdCtf = ctfServicePort.createCtf(ctf, file);

        return ResponseEntity.ok(createdCtf);
    }
    @ApiResponse(responseCode = "200", description = "Ctfs gotten successfully")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    @GetMapping
    public ResponseEntity<List<Ctf>> getAllCtfs() {
        List<Ctf> ctfs = ctfServicePort.getAllCtfs();
        System.out.println("ctfs: " + ctfs.size());
        return ResponseEntity.ok(ctfs);
    }
    @ApiResponse(responseCode = "200", description = "Ctf gotten successfully")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    @GetMapping("/{name}")
    public ResponseEntity<Ctf> getCtf(@PathVariable String name) {
        Ctf ctf = ctfServicePort.getCtfByName(name);
        return ResponseEntity.ok(ctf);
    }
    @ApiResponse(responseCode = "200", description = "Ctf created successfully")
    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    @PutMapping("/{name}")
    public ResponseEntity<Ctf> updateCtf(@PathVariable String name, @RequestBody Ctf ctf) {
        Ctf updatedCtf = ctfServicePort.updateCtf(name, ctf);
        return ResponseEntity.ok(updatedCtf);
    }
    @ApiResponse(responseCode = "200", description = "Ctf deleted successfully")
    @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCtf(@PathVariable String name) {
        ctfServicePort.deleteCtf(name);
        return ResponseEntity.noContent().build();
    }

}
