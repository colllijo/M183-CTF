package dev.coll.ctf.adapter.repository.jpa.ctf.entity;

import java.time.Instant;

import dev.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "SOLVE")
@IdClass(SolveEntityId.class)
public class SolveEntity {
  @Id
  @ManyToOne
  @JoinColumn(name = "ctf_id", referencedColumnName = "id")
  private CtfEntity ctf;

  @Id
  @ManyToOne
  @JoinColumn(name = "user_account_id", referencedColumnName = "id")
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private UserEntity solver;

  @Column(name = "points")
  private Integer points;

  @Column(name = "timestamp")
  private Instant timestamp;

  @Column(name = "rank")
  private Integer rank;
}
