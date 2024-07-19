package dev.coll.ctf.adapter.api.rest.ctf;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import dev.coll.ctf.domain.ctf.model.Ctf;
import dev.coll.ctf.domain.ctf.model.Solve;
import dev.coll.ctf.domain.ctf.port.in.CtfServicePort;
import dev.coll.ctf.domain.user.model.User;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration
@WithMockUser
class CtfControllerTest {
  @Autowired
  private WebApplicationContext context;

  @MockBean
  public CtfServicePort mockCtfService;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  @Test
  void getCtfsShouldReturnCtfs() {
    // Given
    List<Ctf> ctfs = List.of(
      Ctf.builder().name("ctf1").author(User.builder().username("user1").build()).build(),
      Ctf.builder().name("ctf2").author(User.builder().username("user1").build()).build(),
      Ctf.builder().name("ctf3").author(User.builder().username("user1").build()).build()
    );

    // When
    when(mockCtfService.getAllCtfs()).thenReturn(ctfs);

    // Then
    given()
      .when()
        .get("/ctf/").andReturn()
      .then()
        .statusCode(200)
        .body("_embedded.ctfCollection.size()", equalTo(3));
  }

  @Test
  void getCtfShouldReturnCtf() {
    // Given
    Ctf ctf = Ctf.builder().name("ctf1").author(User.builder().username("user1").build()).build();

    // When
    when(mockCtfService.getCtfByName("ctf1")).thenReturn(Optional.of(ctf));

    // Then
    given()
      .when()
        .get("/ctf/ctf1")
      .then()
        .statusCode(200)
        .body("name", equalTo("ctf1"))
        .body("author.username", equalTo("user1"));
  }

  @Test
  void getCtfWithUnknownCtfShouldReturnError() {
    // When
    when(mockCtfService.getCtfByName("ctf1")).thenReturn(Optional.empty());

    // Then
    given()
      .when()
        .get("/ctf/ctf1")
      .then()
        .statusCode(404)
        .body("error", equalTo("CtfNotFoundException"))
        .body("message", equalTo("Ctf not found: Ctf w/ name=ctf1 not found"));
  }

  @Test
  void createCtfShouldReturnCtf() throws Exception {
    // Given
    Ctf ctf = Ctf.builder().name("ctf1").description("Simple Ctf").flag("CCTF{TEST}").author(User.builder().username("user1").build()).build();

    // When
    when(mockCtfService.createCtf(Mockito.any(Ctf.class), Mockito.any())).thenReturn(ctf);

    // Then
    given()
        .contentType("multipart/form-data")
        .multiPart("ctf", "ctf.txt", "{ \"name\": \"ctf1\", \"description\": \"Simple Ctf\", \"flag\": \"CCTF{TEST}\" }".getBytes(), "application/json")
      .when()
        .post("/ctf/")
      .then()
        .statusCode(200)
        .body("name", equalTo("ctf1"))
        .body("description", equalTo("Simple Ctf"))
        .body("author.username", equalTo("user1"));
  }

  @Test
  void createCtfShouldReturnAccessDenied() throws Exception {
    // When
    when(mockCtfService.createCtf(Mockito.any(Ctf.class), Mockito.any())).thenThrow(new AccessDeniedException("Access Denied"));

    // Then
    given()
        .contentType("multipart/form-data")
        .multiPart("ctf", "ctf.txt", "{ \"name\": \"ctf1\", \"description\": \"Simple Ctf\", \"flag\": \"CCTF{TEST}\" }".getBytes(), "application/json")
      .when()
        .post("/ctf/")
      .then()
        .statusCode(403)
        .body("error", equalTo("AccessDeniedException"));
  }

  @Test
  void downloadFileShouldReturnFile() {
    // Given
    ByteArrayResource resource = new ByteArrayResource("test".getBytes());

    // When
    when(mockCtfService.downloadFile("ctf1/test.txt")).thenReturn(resource);

    // Then
    given()
      .when()
        .get("/ctf/download/ctf1/test.txt")
      .then()
        .statusCode(200)
        .body(equalTo("test"));
  }

  @Test
  void solveCtfShouldReturnSolve() {
    // Given
    User author = User.builder().username("author").build();
    User solver = User.builder().username("solver").build();
    Solve solve = Solve.builder().ctf(Ctf.builder().name("ctf1").author(author).build()).solver(solver).points(42).rank(3).build();

    // When
    when(mockCtfService.submitFlag("ctf1", "CCTF{TEST}")).thenReturn(solve);

    // Then
    given()
        .contentType("application/json")
        .body("{ \"flag\": \"CCTF{TEST}\" }")
      .when()
        .post("/ctf/ctf1")
      .then()
        .log().all()
        .statusCode(200)
        .body("points", equalTo(42))
        .body("rank", equalTo(3));
  }
}
