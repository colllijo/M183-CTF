package ch.coll.ctf.domain.token.service;

import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;

import ch.coll.ctf.domain.token.port.in.JwtServicePort;
import ch.coll.ctf.domain.user.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtService implements JwtServicePort {
  @Value("${security.jwt.secret}")
  private String secretKey;

  @Value("${security.jwt.expiration}")
  private Integer expirationTime;

  @Override
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  private Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  private String extractFingerprint(String token) {
    return extractClaim(token, claims -> claims.get("fingerprint", String.class));
  }

  private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
    final Claims claims = extractAllClaims(token);
    return claimsResolvers.apply(claims);
  }

  private Claims extractAllClaims(String token) {
    return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
        .getBody();
  }

  @Override
  public String generateToken(User user, String fingerprint) {
    HashMap<String, Object> extraClaims = new HashMap<>();
    extraClaims.put("role", user.getRole());
    extraClaims.put("fingerprint", fingerprint);

    return generateToken(extraClaims, user);
  }

  private String generateToken(Map<String, Object> extraClaims, User user) {
    return Jwts.builder()
        .setClaims(extraClaims)
        .setSubject(user.getUsername())
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
        .signWith(getSigningKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  @Override
  public boolean isTokenValid(String token, String fingerprint, User user) {
    return Objects.equals(user.getUsername(), extractUsername(token))
        && Objects.equals(hashFingerprint(fingerprint), extractFingerprint(token)) &&
        !isTokenExpired(token);
  }

  @Override
  public String hashFingerprint(String fingerprint) {
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] fingerprintBytes = digest.digest(fingerprint.getBytes());

      return Base64.getEncoder().encodeToString(fingerprintBytes);
    } catch (NoSuchAlgorithmException e) {
      return fingerprint;
    }
  }

  private boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  public Integer getExpirationTime() {
    return expirationTime;
  }

  private Key getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
