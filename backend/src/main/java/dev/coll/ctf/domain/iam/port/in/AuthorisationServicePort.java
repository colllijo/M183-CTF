package dev.coll.ctf.domain.iam.port.in;

import org.springframework.security.access.prepost.PreAuthorize;

import dev.coll.ctf.domain.iam.model.authorisation.Feature;

public interface AuthorisationServicePort {
  @PreAuthorize("isAuthenticated()")
  public boolean checkFeatureAccess(Feature feature);
}
