// package ch.coll.ctf.infrastructure.config;
//
// import java.io.IOException;
//
// import org.springframework.lang.NonNull;
// import
// org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.context.SecurityContextHolder;
// import
// org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
// import org.springframework.stereotype.Component;
// import org.springframework.web.filter.OncePerRequestFilter;
//
// import ch.coll.ctf.domain.token.port.in.JwtServicePort;
// import ch.coll.ctf.domain.user.model.User;
// import ch.coll.ctf.domain.user.port.in.UserServicePort;
// import jakarta.servlet.FilterChain;
// import jakarta.servlet.ServletException;
// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.servlet.http.HttpServletResponse;
// import lombok.RequiredArgsConstructor;
// import lombok.extern.slf4j.Slf4j;
//
// @Slf4j
// @Component
// @RequiredArgsConstructor
// public class JwtAuthenticationFilter extends OncePerRequestFilter {
// private final JwtServicePort jwtService;
// private final UserServicePort userService;
//
// @Override
// protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull
// HttpServletResponse response,
// @NonNull FilterChain filterChain) throws ServletException, IOException {
// log.info("Filtering request with JwtAuthenticationFilter");
// final String authorizationHeader = request.getHeader("Authorization");
//
// if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer
// ")) {
// filterChain.doFilter(request, response);
// return;
// }
//
// log.info("Authorization header: {}", authorizationHeader);
// final String jwt = authorizationHeader.substring("Bearer ".length());
// final String username = jwtService.extractUsername(jwt);
//
// if (username != null && !username.isEmpty() &&
// SecurityContextHolder.getContext().getAuthentication() == null) {
// User user = userService.getUserByUsername(username).orElseThrow(() -> new
// RuntimeException("User not found"));
//
// if (jwtService.isTokenValid(jwt, user)) {
// UsernamePasswordAuthenticationToken authToken = new
// UsernamePasswordAuthenticationToken(user, null);
// authToken.setDetails(new
// WebAuthenticationDetailsSource().buildDetails(request));
//
// SecurityContextHolder.getContext().setAuthentication(authToken);
// }
// }
//
// filterChain.doFilter(request, response);
// }
// }
