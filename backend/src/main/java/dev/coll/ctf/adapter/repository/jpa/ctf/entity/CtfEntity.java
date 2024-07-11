package dev.coll.ctf.adapter.repository.jpa.ctf.entity;

import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.IDENTITY;

import java.util.Set;

import dev.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "CTF")
public class CtfEntity {
  @Id
  @GeneratedValue(strategy = IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "author_id")
  private UserEntity author;

  @Column(name = "flag")
  private String flag;

  @Column(name = "file_path")
  private String filePath;

  @OneToMany(mappedBy = "ctf", fetch = EAGER)
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  private Set<SolveEntity> solves;
}
