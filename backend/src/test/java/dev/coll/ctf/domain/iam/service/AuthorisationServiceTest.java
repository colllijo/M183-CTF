package dev.coll.ctf.domain.iam.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import dev.coll.ctf.domain.iam.model.authorisation.Feature;
import dev.coll.ctf.domain.iam.model.authorisation.Roles;
import dev.coll.ctf.domain.user.model.User;

class AuthorisationServiceTest {
  private AuthorisationService testee;

  @BeforeEach
  void setup() {
    testee = new AuthorisationService();
  }

  @Test
  void checkFeatureAccessShouldReturnTrue() {
    // Given
    User user = User.builder().username("user1").roles(Set.of(Roles.ADMIN.getRole())).build();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, List.of(new SimpleGrantedAuthority(Roles.ADMIN.getAuthorityName())));
    SecurityContextHolder.getContext().setAuthentication(token);

    // When
    boolean result = testee.checkFeatureAccess(Feature.ADMINISTRATION);

    // Then
    SecurityContextHolder.getContext().setAuthentication(authentication);

    assertThat(result).isTrue();
  }

  @Test
  void checkFeatureAccessShouldReturnFalse() {
    // Given
    User user = User.builder().username("user1").build();

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user, null, null);
    SecurityContextHolder.getContext().setAuthentication(token);


    // When
    boolean result = testee.checkFeatureAccess(Feature.ADMINISTRATION);

    // Then
    SecurityContextHolder.getContext().setAuthentication(authentication);

    assertThat(result).isFalse();
  }
}
