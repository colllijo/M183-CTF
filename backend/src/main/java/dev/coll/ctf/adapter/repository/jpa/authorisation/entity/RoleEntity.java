package dev.coll.ctf.adapter.repository.jpa.authorisation.entity;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.Set;

import dev.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
  @Column(name = "ID")
  private Long id;

  @Column(name = "NAME", unique = true, nullable = false)
  private String name;

  @Column(name = "DESCRIPTION")
  private String description;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToMany(mappedBy = "roles", fetch = EAGER)
  private Set<UserEntity> users;

  @OneToMany(mappedBy = "role", fetch = EAGER)
  private Set<PermissionEntity> permissions;
}
