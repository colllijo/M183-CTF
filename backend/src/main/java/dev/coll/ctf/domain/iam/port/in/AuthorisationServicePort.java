package dev.coll.ctf.domain.iam.port.in;

import dev.coll.ctf.domain.iam.model.annotation.RequireAuthenticated;
import dev.coll.ctf.domain.iam.model.authorisation.Feature;

public interface AuthorisationServicePort {
  @RequireAuthenticated
  public boolean checkFeatureAccess(Feature feature);
}
