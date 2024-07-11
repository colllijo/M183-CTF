package dev.coll.ctf.adapter.repository.jpa.authorisation.entity;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.Set;

import dev.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ROLE")
public class RoleEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", unique = true, nullable = false)
  private String name;

  @Column(name = "description")
  private String description;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(mappedBy = "roles", fetch = EAGER)
  private Set<UserEntity> users;

  @Enumerated(EnumType.STRING)
  @Column(name = "permissions")
  private Set<Permission> permissions;
}
