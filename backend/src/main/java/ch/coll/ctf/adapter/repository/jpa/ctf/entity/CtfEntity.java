package ch.coll.ctf.adapter.repository.jpa.ctf.entity;

import static jakarta.persistence.GenerationType.IDENTITY;
import static jakarta.persistence.FetchType.EAGER;

import java.util.Set;

import ch.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "_CaptureTheFlag")
public class CtfEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name")
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private UserEntity author;

  @Column(name = "flag")
  private String flag;

  @Column(name = "points")
  private Integer points;

  @OneToMany(mappedBy = "ctf", fetch = EAGER)
  private Set<SolveEntity> solves;
}
