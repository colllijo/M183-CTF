package dev.coll.ctf.domain.iam.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.iam.model.authorisation.Roles;
import dev.coll.ctf.domain.iam.model.exception.RoleNotFoundException;
import dev.coll.ctf.domain.iam.port.out.AuthorisationRepositoryPort;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.model.exception.UserNotFoundException;
import dev.coll.ctf.domain.user.port.out.UserRepositoryPort;

class AdministrationServiceTest {
  private AdministrationService testee;

  private AuthorisationRepositoryPort mockAuthorisationRepository;
  private UserRepositoryPort mockUserRepository;

  @BeforeEach
  void setup() {
    mockAuthorisationRepository = mock(AuthorisationRepositoryPort.class);
    mockUserRepository = mock(UserRepositoryPort.class);

    testee = new AdministrationService(mockAuthorisationRepository, mockUserRepository);
  }

  @Test
  void getUsersShouldReturnUsers() {
    // Given
    List<User> users = List.of(
        User.builder().username("user1").build(),
        User.builder().username("user2").build());

    // When
    when(mockUserRepository.getUsers()).thenReturn(users);

    List<User> result = testee.getUsers();

    // Then
    assertThat(result).isEqualTo(users);
    verify(mockUserRepository, times(1)).getUsers();
  }

  @Test
  void getUserByUsernameShouldReturnUser() {
    // Given
    User user = User.builder().username("user1").build();

    // When
    when(mockUserRepository.getUserByUsername("user1")).thenReturn(Optional.of(user));

    Optional<User> result = testee.getUserByUsername("user1");

    // Then
    assertThat(result).isPresent().get().isEqualTo(user);
    verify(mockUserRepository, times(1)).getUserByUsername("user1");
  }

  @Test
  void addRoleToUserShouldAddRoleToUser() {
    // Given
    User user = User.builder().username("user1").build();

    Role role = Role.builder().name("role1").build();
    User expectedUser = User.builder().username("user1").roles(Set.of(role, Roles.USER.getRole())).build();

    // When
    when(mockUserRepository.getUserByUsername("user1")).thenReturn(Optional.of(user));
    when(mockAuthorisationRepository.getRoleByName("role1")).thenReturn(Optional.of(role));
    when(mockUserRepository.updateUser(expectedUser)).thenReturn(expectedUser);

    User result = testee.addRoleToUser("user1", "role1");

    // Then
    assertThat(result).isEqualTo(expectedUser);
    verify(mockUserRepository, times(1)).getUserByUsername("user1");
    verify(mockAuthorisationRepository, times(1)).getRoleByName("role1");
    verify(mockUserRepository, times(1)).updateUser(expectedUser);
  }

  @Test
  void addRoleToUserWithUnknowUserShouldThrowException() {
    // When
    when(mockUserRepository.getUserByUsername(anyString())).thenReturn(Optional.empty());

    // Then
    assertThatThrownBy(() -> testee.addRoleToUser("user1", "role1")).isInstanceOf(UserNotFoundException.class)
        .hasMessage("User not found: User w/ username=user1 not found");
    verify(mockUserRepository, times(1)).getUserByUsername("user1");
  }

  @Test
  void addRoleToUserWithUnknownRoleShouldThrowException() {
    // Given
    User user = User.builder().username("user1").build();

    // When
    when(mockUserRepository.getUserByUsername("user1")).thenReturn(Optional.of(user));
    when(mockAuthorisationRepository.getRoleByName("role1")).thenReturn(Optional.empty());

    // Then
    assertThatThrownBy(() -> testee.addRoleToUser("user1", "role1")).isInstanceOf(RoleNotFoundException.class)
        .hasMessage("Role not found: Role w/ name=role1 not found");
    verify(mockUserRepository, times(1)).getUserByUsername("user1");
    verify(mockAuthorisationRepository, times(1)).getRoleByName("role1");
  }

  @Test
  void addRoleToUserForOwnedRoleShouldDoNothing() {
    Role role = Role.builder().name("role1").build();
    User user = User.builder().username("user1").roles(Set.of(role)).build();

    // When
    when(mockUserRepository.getUserByUsername("user1")).thenReturn(Optional.of(user));
    when(mockAuthorisationRepository.getRoleByName("role1")).thenReturn(Optional.of(role));

    User result = testee.addRoleToUser("user1", "role1");

    // Then
    assertThat(result).isEqualTo(user);
    verify(mockUserRepository, times(1)).getUserByUsername("user1");
    verify(mockAuthorisationRepository, times(1)).getRoleByName("role1");
  }

  @Test
  void removeRoleFromUserShouldRemoveRoleFromUser() {
    // Given
    Role role = Role.builder().name("role1").build();
    User user = User.builder().username("user1").roles(new HashSet<>(List.of(role, Roles.USER.getRole()))).build();

    User expectedUser = User.builder().username("user1").roles(Set.of(Roles.USER.getRole())).build();

    // When
    when(mockUserRepository.getUserByUsername("user1")).thenReturn(Optional.of(user));
    when(mockAuthorisationRepository.getRoleByName("role1")).thenReturn(Optional.of(role));
    when(mockUserRepository.updateUser(expectedUser)).thenReturn(expectedUser);

    User result = testee.removeRoleFromUser("user1", "role1");

    // Then
    assertThat(result).isEqualTo(expectedUser);
    verify(mockUserRepository, times(1)).getUserByUsername("user1");
    verify(mockAuthorisationRepository, times(1)).getRoleByName("role1");
    verify(mockUserRepository, times(1)).updateUser(expectedUser);
  }

  @Test
  void removeRoleFromUserWithUnknowUserShouldThrowException() {
    // When
    when(mockUserRepository.getUserByUsername(anyString())).thenReturn(Optional.empty());

    // Then
    assertThatThrownBy(() -> testee.removeRoleFromUser("user1", "role1")).isInstanceOf(UserNotFoundException.class)
        .hasMessage("User not found: User w/ username=user1 not found");
    verify(mockUserRepository, times(1)).getUserByUsername("user1");
  }

  @Test
  void removeRoleFromUserWithUnknownRoleShouldThrowException() {
    // Given
    User user = User.builder().username("user1").build();

    // When
    when(mockUserRepository.getUserByUsername("user1")).thenReturn(Optional.of(user));
    when(mockAuthorisationRepository.getRoleByName("role1")).thenReturn(Optional.empty());

    // Then
    assertThatThrownBy(() -> testee.removeRoleFromUser("user1", "role1")).isInstanceOf(RoleNotFoundException.class)
        .hasMessage("Role not found: Role w/ name=role1 not found");
    verify(mockUserRepository, times(1)).getUserByUsername("user1");
    verify(mockAuthorisationRepository, times(1)).getRoleByName("role1");
  }

  @Test
  void removeRoleFromUserForUnownedRoleShouldDoNothing() {
    Role role = Role.builder().name("role1").build();
    User user = User.builder().username("user1").build();

    // When
    when(mockUserRepository.getUserByUsername("user1")).thenReturn(Optional.of(user));
    when(mockAuthorisationRepository.getRoleByName("role1")).thenReturn(Optional.of(role));

    User result = testee.removeRoleFromUser("user1", "role1");

    // Then
    assertThat(result).isEqualTo(user);
    verify(mockUserRepository, times(1)).getUserByUsername("user1");
    verify(mockAuthorisationRepository, times(1)).getRoleByName("role1");
  }

  @Test
  void getRolesShouldReturnRoles() {
    // Given
    List<Role> roles = List.of(
        Role.builder().name("role1").build(),
        Role.builder().name("role2").build());

    // When
    when(mockAuthorisationRepository.getRoles()).thenReturn(roles);

    List<Role> result = testee.getRoles();

    // Then
    assertThat(result).isEqualTo(roles);
  }

  @Test
  void getRoleByNameShouldReturnRole() {
    // Given
    Role role = Role.builder().name("role1").build();

    // When
    when(mockAuthorisationRepository.getRoleByName("role1")).thenReturn(Optional.of(role));

    Optional<Role> result = testee.getRoleByName("role1");

    // Then
    assertThat(result).isPresent().get().isEqualTo(role);
  }

  @Test
  void getPermissionsReturnsPermissionValues() {
    assertThat(testee.getPermissions()).containsExactlyInAnyOrderElementsOf(List.of(Permission.values()));
  }
}
