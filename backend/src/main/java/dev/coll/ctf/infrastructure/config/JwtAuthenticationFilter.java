package dev.coll.ctf.infrastructure.config;

import java.io.IOException;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import dev.coll.ctf.domain.jwt.model.SecureToken;
import dev.coll.ctf.domain.jwt.port.in.JwtServicePort;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.port.in.UserServicePort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final JwtServicePort jwtService;
  private final UserServicePort userService;

  @Override
  public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    final String token = authorizationHeader.substring("Bearer ".length());
    final String username = jwtService.extractUsername(token);
    final String fingerprint = Stream.of(request.getCookies())
        .filter(cookie -> cookie.getName().equals("Access-Token"))
        .map(Cookie::getValue)
        .findFirst()
        .orElse(null);

    final SecureToken secureToken = SecureToken.builder()
      .value(token)
      .fingerprint(fingerprint)
      .build();

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      final User user = userService.getUserByUsername(username)
          .orElseThrow(() -> new RuntimeException("User not found"));

      if (jwtService.isTokenValid(secureToken, user)) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null, userService.getAuthorities(user));
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
      }
    }

    filterChain.doFilter(request, response);
  }
}
