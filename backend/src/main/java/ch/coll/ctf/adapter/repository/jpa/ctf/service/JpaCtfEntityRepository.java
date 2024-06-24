package ch.coll.ctf.adapter.repository.jpa.ctf.service;

import ch.coll.ctf.domain.ctf.model.Ctf;
import org.springframework.data.jpa.repository.JpaRepository;

import ch.coll.ctf.adapter.repository.jpa.ctf.entity.CtfEntity;

import java.util.List;
import java.util.Optional;

public interface JpaCtfEntityRepository extends JpaRepository<CtfEntity, Long> {
    List<CtfEntity> findAll();
    Optional<CtfEntity> findByName(String name);
    CtfEntity save(CtfEntity ctfEntity);
    void deleteByName(String name);
}
