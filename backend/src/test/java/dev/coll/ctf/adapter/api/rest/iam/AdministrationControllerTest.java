package dev.coll.ctf.adapter.api.rest.iam;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.WebApplicationContext;

import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.iam.model.authorisation.Roles;
import dev.coll.ctf.domain.iam.port.in.AdministrationServicePort;
import dev.coll.ctf.domain.user.model.User;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WithMockUser
class AdministrationControllerTest {
  @Autowired
  private WebApplicationContext context;

  @MockBean
  private AdministrationServicePort mockAdministrationService;

  @BeforeEach
  void setup() {
    RestAssuredMockMvc.webAppContextSetup(context);
  }

  @Test
  void getUsersShouldReturnUsers() {
    // Given
    List<User> users = List.of(
      User.builder().username("user1").build(),
      User.builder().username("user2").build(),
      User.builder().username("user3").build()
    );

    // When
    when(mockAdministrationService.getUsers()).thenReturn(users);

    // Then
    given()
      .when()
        .get("/administration/users")
      .then()
        .statusCode(200)
        .body("_embedded.userDetailsCollection.size()", equalTo(3));
  }

  @Test
  void getRolesShouldReturnRoles() {
    // Given
    List<Role> roles = Stream.of(Roles.values()).map(Roles::getRole).toList();

    // When
    when(mockAdministrationService.getRoles()).thenReturn(roles);

    // Then
    given()
      .when()
        .get("/administration/roles")
      .then()
        .statusCode(200)
        .body("_embedded.roleCollection.size()", equalTo(roles.size()));
  }


  @Test
  void getPermissionsShouldReturnPermissions() {
    // When
    when(mockAdministrationService.getPermissions()).thenReturn(List.of(Permission.values()));

    // Then
    given()
      .when()
        .get("/administration/permissions")
      .then()
        .statusCode(200)
        .body("_embedded.permissionCollection.size()", equalTo(Permission.values().length));
  }
}
