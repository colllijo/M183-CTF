package ch.coll.ctf.adapter.repository.jpa.ctf.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.Set;

import ch.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
@Table(name = "_CaptureTheFlag")
public class CaptureTheFlagEntity {
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

  @ManyToMany
  @JoinTable(name = "_Solve", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "ctf_id"))
  private Set<UserEntity> solvers;
}
