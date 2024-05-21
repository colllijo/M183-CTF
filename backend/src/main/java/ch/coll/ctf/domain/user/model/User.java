package ch.coll.ctf.domain.user.model;

import java.util.Set;

import ch.coll.ctf.domain.ctf.model.CaptureTheFlag;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {
  private Long id;
  private String username;
  private String password;
  private String email;
  private UserRole userRole;
  private boolean active;
  private Set<CaptureTheFlag> solvedChallenges;
}
