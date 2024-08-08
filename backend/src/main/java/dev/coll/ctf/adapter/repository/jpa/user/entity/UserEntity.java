package dev.coll.ctf.adapter.repository.jpa.user.entity;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.Set;

import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.RoleEntity;
import dev.coll.ctf.adapter.repository.jpa.ctf.entity.SolveEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "USER_ACCOUNT")
public class UserEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "ID")
  private Long id;

  @Column(name = "USERNAME", unique = true, nullable = false)
  private String username;

  @Column(name = "PASSWORD", nullable = false)
  private String password;

  @Column(name = "EMAIL", nullable = false)
  private String email;

  @OneToMany(mappedBy = "solver", fetch = EAGER)
  private Set<SolveEntity> solves;

  @Column(name = "ACTIVE")
  private boolean active;

  @ManyToMany(fetch = EAGER)
  @JoinTable(
    name = "USER_ACCOUNT_ROLE",
    joinColumns = @JoinColumn(name = "USER_ACCOUNT_ID"),
    inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
  )
  private Set<RoleEntity> roles;
}
