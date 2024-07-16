package dev.coll.ctf.domain.jwt.service;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.keygen.KeyGenerators;

import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.jwt.model.SecureToken;
import dev.coll.ctf.domain.jwt.port.in.JwtServicePort;
import dev.coll.ctf.domain.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtService implements JwtServicePort {
  private static final String FINGERPRINT_CLAIM = "fingerprint";
  private static final Integer FINGERPRINT_LENGTH = 256;

  @Value("${security.jwt.secret}")
  private String secretKey;

  @Value("${security.jwt.lifetime.access}")
  private Integer accessTokenLifetime;

  @Value("${security.jwt.lifetime.refresh}")
  private Integer refreshTokenLifetime;

  @Override
  public SecureToken generateSecureAccessToken(User user) {
    HashMap<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("roles", user.getRoles().stream().map(Role::getName).toList());

    return generateToken(extraClaims, user, accessTokenLifetime);
  }

  @Override
  public SecureToken generateSecureRefreshToken(User user) {
    HashMap<String, Object> extraClaims = new HashMap<>();

    return generateToken(extraClaims, user, refreshTokenLifetime);
  }

  private SecureToken generateToken(Map<String, Object> extraClaims, User user, Integer lifetime) {
    String fingerprint = generateFingerprint();
    extraClaims.put(FINGERPRINT_CLAIM, hashFingerprint(fingerprint));

    String token = Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + lifetime))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();

    return SecureToken.builder()
      .value(token)
      .fingerprint(fingerprint)
      .lifeTime(lifetime)
      .build();
  }

  private String hashFingerprint(String fingerprint) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] fingerprintBytes = digest.digest(fingerprint.getBytes());

      return Base64.getEncoder().encodeToString(fingerprintBytes);
    } catch (NoSuchAlgorithmException e) {
      return fingerprint;
    }
  }

  @Override
  public boolean isTokenValid(SecureToken token, User user) {
    return Objects.equals(user.getUsername(), extractUsername(token.value()))
        && Objects.equals(hashFingerprint(token.fingerprint()), extractFingerprint(token.value()))
        && !isTokenExpired(token.value());
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private String extractFingerprint(String token) {
    return extractClaim(token, claims -> claims.get(FINGERPRINT_CLAIM, String.class));
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    try {
      final Claims claims = extractAllClaims(token);
      return claimsResolvers.apply(claims);
    } catch (ExpiredJwtException e) {
      return null;
    }
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
  }

  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
  }

  private String generateFingerprint() {
    return HexFormat.of().formatHex(KeyGenerators.secureRandom(FINGERPRINT_LENGTH).generateKey());
  }
}
