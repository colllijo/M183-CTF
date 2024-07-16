package dev.coll.ctf.domain.jwt.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.test.util.ReflectionTestUtils;

import dev.coll.ctf.domain.iam.model.authorisation.Roles;
import dev.coll.ctf.domain.jwt.model.SecureToken;
import dev.coll.ctf.domain.user.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

class JwtServiceTest {
  private JwtService testee;

  private String secret;

  @BeforeEach
  void setup() {
    secret = HexFormat.of().formatHex(KeyGenerators.secureRandom(64).generateKey());
    testee = new JwtService();

    ReflectionTestUtils.setField(testee, "secretKey", secret);
    ReflectionTestUtils.setField(testee, "accessTokenLifetime", 900000);
    ReflectionTestUtils.setField(testee, "refreshTokenLifetime", 86400000);
  }

  @Test
  void generateSecureAccessTokenShouldReturnSecureToken() {
    // Given
    User user = User.builder().roles(Set.of(Roles.USER.getRole())).build();

    // When
    SecureToken result = testee.generateSecureAccessToken(user);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.value()).isNotNull();
    assertThat(testee.extractUsername(result.value())).isEqualTo(user.getUsername());
    assertThat(result.fingerprint()).isNotNull();
    assertThat(result.lifeTime()).isEqualTo(900000);
  }

  @Test
  void generateSecureRefreshTokenShouldReturnSecureToken() {
    // Given
    User user = User.builder().roles(Set.of(Roles.USER.getRole())).build();

    // When
    SecureToken result = testee.generateSecureRefreshToken(user);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.value()).isNotNull();
    assertThat(testee.extractUsername(result.value())).isEqualTo(user.getUsername());
    assertThat(result.fingerprint()).isNotNull();
    assertThat(result.lifeTime()).isEqualTo(86400000);
  }

  @Test
  void isTokenValidShouldBeTrue() {
    // Given
    User user = User.builder().username("user1").roles(Set.of(Roles.USER.getRole())).build();
    String token = generateTestToken(user, new Date(), new HashMap<>());
    SecureToken accessToken = SecureToken.builder().value(token).fingerprint("fingerprint").build();

    // When
    boolean result = testee.isTokenValid(accessToken, user);

    // Then
    assertThat(result).isTrue();
  }

  @Test
  void isTokenValidWithWrongUsernameShouldBeFalse() {
    // Given
    User user = User.builder().username("user1").roles(Set.of(Roles.USER.getRole())).build();
    String token = generateTestToken(user, new Date(), new HashMap<>());
    SecureToken accessToken = SecureToken.builder().value(token).fingerprint("fingerprint").build();

    user.setUsername("user2");

    // When
    boolean result = testee.isTokenValid(accessToken, user);

    // Then
    assertThat(result).isFalse();
  }

  @Test
  void isTokenValidWithWrongFingerprintShouldBeFalse() {
    // Given
    User user = User.builder().username("user1").roles(Set.of(Roles.USER.getRole())).build();
    String token = generateTestToken(user, new Date(), new HashMap<>());
    SecureToken accessToken = SecureToken.builder().value(token).fingerprint("bad-fingerprint").build();

    // When
    boolean result = testee.isTokenValid(accessToken, user);

    // Then
    assertThat(result).isFalse();
  }

  @Test
  void isTokenValidWithExpiredTokenShouldBeFalse() {
    // Given
    User user = User.builder().username("user1").roles(Set.of(Roles.USER.getRole())).build();
    String token = generateTestToken(user, new Date(System.currentTimeMillis() - 1000000000), new HashMap<>());
    SecureToken accessToken = SecureToken.builder().value(token).fingerprint("fingerprint").build();

    // When
    boolean result = testee.isTokenValid(accessToken, user);

    // Then
    assertThat(result).isFalse();
  }

  @Test
  void extractUsernameShouldReturnUsername() {
    // Given
    User user = User.builder().username("user1").roles(Set.of(Roles.USER.getRole())).build();
    String token = generateTestToken(user, new Date(), new HashMap<>());

    // When
    String result = testee.extractUsername(token);

    // Then
    assertThat(result).isEqualTo(user.getUsername());
  }

  String generateTestToken(User user, Date issuedAt, Map<String, Object> claims) {
    claims.put("fingerprint", "RIY7A+mQm3EA4FsCUmkJo0b9dFUYP2YZ4P5hmMiZgeA=");

    return Jwts.builder()
      .addClaims(claims)
      .setSubject(user.getUsername())
      .setIssuedAt(issuedAt)
      .setExpiration(new Date(issuedAt.getTime() + 900000))
      .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret)), SignatureAlgorithm.HS256)
      .compact();
  }
}
