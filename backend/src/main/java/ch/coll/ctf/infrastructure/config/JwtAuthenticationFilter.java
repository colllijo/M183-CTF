package ch.coll.ctf.infrastructure.config;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import ch.coll.ctf.domain.token.port.in.JwtServicePort;
import ch.coll.ctf.domain.user.model.User;
import ch.coll.ctf.domain.user.port.in.UserServicePort;
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

  private final HandlerExceptionResolver handlerExceptionResolver;

  @Override
  public void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    final String authorizationHeader = request.getHeader("Authorization");

    if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      final String token = authorizationHeader.substring("Bearer ".length());
      final String username = jwtService.extractUsername(token);
      final String fingerprint = Stream.of(request.getCookies())
          .filter(cookie -> cookie.getName().equals("Access-Token"))
          .map(Cookie::getValue)
          .findFirst()
          .orElse(null);

      if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        final User user = userService.getUserByUsername(username)
            .orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println(user);
        if (jwtService.isTokenValid(token, fingerprint, user)) {
          UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null,
              List.of());
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }

      filterChain.doFilter(request, response);
    } catch (Exception e) {
      // Check if the exception is relevant for the requested path
      if (new AntPathRequestMatcher("/auth/**").matches(request)
          || new AntPathRequestMatcher("/docs/**").matches(request)) {
        filterChain.doFilter(request, response);
        return;
      }
      handlerExceptionResolver.resolveException(request, response, null, e);
    }
  }
}
