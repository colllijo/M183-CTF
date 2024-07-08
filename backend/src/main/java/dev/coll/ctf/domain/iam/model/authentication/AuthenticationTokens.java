package dev.coll.ctf.domain.iam.model.authentication;

import dev.coll.ctf.domain.jwt.model.SecureToken;
import lombok.Builder;

@Builder
public record AuthenticationTokens(SecureToken accessToken, SecureToken refreshToken) {}
