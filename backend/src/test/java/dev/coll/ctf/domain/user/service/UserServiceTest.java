package dev.coll.ctf.domain.user.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.coll.ctf.domain.iam.model.authorisation.Roles;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.out.UserRepositoryPort;

class UserServiceTest {
  private UserService testee;

  private PasswordEncoder mockPasswordEncoder;
  private UserRepositoryPort mockUserRepository;

  @BeforeEach
  void setup() {
    mockPasswordEncoder = mock(PasswordEncoder.class);
    mockUserRepository = mock(UserRepositoryPort.class);

    testee = new UserService(mockPasswordEncoder, mockUserRepository);
  }

  @Test
  void getUsersShouldReturnUsers() {
    // Given
    List<User> users = List.of(
      User.builder().username("user1").build(),
      User.builder().username("user2").build()
    );

    // When
    when(mockUserRepository.getUsers()).thenReturn(users);

    List<User> result = testee.getUsers();

    // Then
    assertThat(result).hasSize(2).extracting("username").containsExactly("user1", "user2");
  }

  @Test
  void getUsersShouldNotReturnAdminUsers() {
    // Given
    List<User> users = List.of(
      User.builder().username("user1").roles(Set.of()).build(),
      User.builder().username("user2").roles(Set.of()).build(),
      User.builder().username("admin").roles(Set.of(Roles.ADMIN.getRole())).build()
    );

    // When
    when(mockUserRepository.getUsers()).thenReturn(users);

    List<User> result = testee.getUsers();

    // Then

    assertThat(result).hasSize(2).extracting("username").containsExactly("user1", "user2");
  }

  @Test
  void getUserByUsernameShouldReturnUser() {
    // Given
    User user = User.builder().username("user1").build();

    // When
    when(mockUserRepository.getUserByUsername("user1")).thenReturn(Optional.of(user));

    Optional<User> result = testee.getUserByUsername("user1");

    // Then
    assertThat(result).isPresent().get().extracting("username").isEqualTo("user1");
  }

  @Test
  void getUserByUsernameShouldReturnEmptyForUnfoundUser() {
    // When
    when(mockUserRepository.getUserByUsername("user1")).thenReturn(Optional.empty());

    Optional<User> result = testee.getUserByUsername("user1");

    // Then
    assertThat(result).isEmpty();
  }

  @Test
  void createUserShouldCreateUserWithEncodedPassword() {
    // Given
    User user = User.builder().username("user1").password("password").build();
    User userWithEncodedPassword = User.builder().username("user1").password("encodedPassword").build();

    // When
    when(mockPasswordEncoder.encode("password")).thenReturn("encodedPassword");
    when(mockUserRepository.createUser(user)).thenReturn(userWithEncodedPassword);

    User result = testee.createUser(user);

    // Then
    assertThat(result).extracting("username", "password").containsExactly("user1", "encodedPassword");
  }

  @Test
  void updateUserShouldUpdateUser() {
    // Given
    User user = User.builder().username("user1").build();
    User userUpdated = User.builder().username("user2").build();

    // When
    when(mockUserRepository.updateUser(user)).thenReturn(userUpdated);

    User result = testee.updateUser(user);

    // Then
    assertThat(result).extracting("username").isEqualTo("user2");
  }

  @Test
  void getAuthoritiesShouldReturnAuthorities() {
    // Given
    User user = User.builder().roles(Set.of(Roles.USER.getRole())).build();

    // When
    List<? extends GrantedAuthority> result = testee.getAuthorities(user);

    // Then
    assertThat(result.stream().map(GrantedAuthority::getAuthority).toList())
      .containsAll(List.of("ROLE_USER", "SUBMIT_FLAG"));
  }
}
