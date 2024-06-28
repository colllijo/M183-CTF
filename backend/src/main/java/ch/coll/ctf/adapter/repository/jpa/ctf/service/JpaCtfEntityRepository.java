package ch.coll.ctf.adapter.repository.jpa.ctf.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.coll.ctf.adapter.repository.jpa.ctf.entity.CtfEntity;

public interface JpaCtfEntityRepository extends JpaRepository<CtfEntity, Long> {
  Optional<CtfEntity> findByName(String name);

  void deleteByName(String name);
}
