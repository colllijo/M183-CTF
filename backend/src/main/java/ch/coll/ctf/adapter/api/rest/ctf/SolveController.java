package ch.coll.ctf.adapter.api.rest.ctf;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/solve")
@RequiredArgsConstructor
public class SolveController {
    // private final SolveServicePort solveServicePort;
    //
    // @ApiResponse(responseCode = "200", description = "Solve created successfully")
    // @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    // @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    // @PostMapping
    // public ResponseEntity<Solve> createSolve(@RequestBody Solve Solve) {
    //     Solve createdSolve = solveServicePort.createSolve(Solve);
    //     return ResponseEntity.ok(createdSolve);
    // }
    // @ApiResponse(responseCode = "200", description = "Solves gotten successfully")
    // @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    // @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    // @GetMapping()
    // public ResponseEntity<List<Solve>> getAllSolves() {
    //     List<Solve> Solves = solveServicePort.getAllSolves();
    //     return ResponseEntity.ok(Solves);
    // }
    // @ApiResponse(responseCode = "200", description = "Solve gotten successfully")
    // @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    // @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(name = "RestErrorResponse", implementation = RestExceptionResponse.class)))
    // @GetMapping("/{username}")
    // public ResponseEntity<List<Solve>> getSolve(@PathVariable String name) {
    //     List<Solve> solveList = solveServicePort.getSolveByUsername(name);
    //     return ResponseEntity.ok(solveList);
    // }
}
