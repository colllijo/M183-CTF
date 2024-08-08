package dev.coll.ctf.adapter.repository.jpa.authorisation.entity;

import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "PERMISSION")
@IdClass(PermissionEntityId.class)
public class PermissionEntity {
  @Id
  @Column(name = "PERMISSION", columnDefinition="VARCHAR(64)")
  @Enumerated(EnumType.STRING)
  private Permission permission;

  @Id
  @ManyToOne
  @JoinColumn(name = "ROLE_ID", referencedColumnName = "ID")
  private RoleEntity role;
}
