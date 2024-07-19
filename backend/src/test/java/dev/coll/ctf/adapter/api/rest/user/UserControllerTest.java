package dev.coll.ctf.adapter.api.rest.user;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.web.context.WebApplicationContext;

import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@WithMockUser
@SpringBootTest
class UserControllerTest {
  @Autowired
  private WebApplicationContext context;

  @MockBean
  private UserServicePort mockUserService;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  @Test
  void getUsersReturnsUsers() {
    // Given
    List<User> users = List.of(
      User.builder().username("user1").build(),
      User.builder().username("user2").build()
    );

    // When
    when(mockUserService.getUsers()).thenReturn(users);

    // then
    given()
      .when()
        .get("/users/")
      .then()
        .statusCode(200)
        .body("_embedded.userInfoCollection.size()", is(2));
  }
}
