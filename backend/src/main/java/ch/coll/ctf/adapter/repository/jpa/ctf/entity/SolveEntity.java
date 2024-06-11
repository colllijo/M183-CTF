package ch.coll.ctf.adapter.repository.jpa.ctf.entity;

import java.time.Instant;

import ch.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "SOLVE")
@IdClass(SolveEntityId.class)
public class SolveEntity {
  @Id
  @ManyToOne
  @JoinColumn(name = "ctf_id", referencedColumnName = "id")
  private CaptureTheFlagEntity ctf;

  @Id
  @ManyToOne
  @JoinColumn(name = "user_account_id", referencedColumnName = "id")
  private UserEntity solver;

  @Column(name = "points")
  private Integer points;

  @Column(name = "timestamp")
  private Instant timestamp;

  @Column(name = "rank")
  private Integer rank;
}
