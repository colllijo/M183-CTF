package ch.coll.ctf.domain.user.model;

import java.util.Set;

import ch.coll.ctf.domain.ctf.model.Solve;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
public class User {
  private Long id;

  @NotBlank
  private String username;

  @Size(min = 12)
  @NotBlank
  private String password;

  @Email
  @NotNull
  private String email;

  @NotNull
  @Builder.Default
  private UserRole role = UserRole.USER;

  @NotNull
  @Builder.Default
  private boolean active = false;

  @NotNull
  private Set<@Valid Solve> solves;
}
