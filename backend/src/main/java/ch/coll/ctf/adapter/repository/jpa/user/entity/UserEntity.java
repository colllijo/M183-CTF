package ch.coll.ctf.adapter.repository.jpa.user.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.FetchType.EAGER;

import java.util.Set;

import ch.coll.ctf.adapter.repository.jpa.ctf.entity.SolveEntity;
import ch.coll.ctf.domain.user.model.UserRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "_User")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Enumerated(EnumType.STRING)
  @Column(name = "role", nullable = false)
  private UserRole role;

  @OneToMany(mappedBy = "solver", fetch = EAGER)
  private Set<SolveEntity> solves;

  @Column(name = "active")
  private boolean active;
}
