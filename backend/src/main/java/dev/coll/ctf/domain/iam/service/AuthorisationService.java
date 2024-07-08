package dev.coll.ctf.domain.iam.service;

import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import dev.coll.ctf.domain.iam.model.authorisation.Feature;
import dev.coll.ctf.domain.iam.port.in.AuthorisationServicePort;

public class AuthorisationService implements AuthorisationServicePort {
  @Override
  public boolean checkFeatureAccess(Feature feature) {
    List<String> authorities = getUserAuthorities();

    return !Collections.disjoint(authorities, feature.getAllowedRoles());
  }

  private List<String> getUserAuthorities() {
    return SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
      .map(GrantedAuthority::getAuthority)
      .toList();
  }
}
