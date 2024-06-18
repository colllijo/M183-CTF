package ch.coll.ctf.adapter.repository.jpa.ctf.service;

import ch.coll.ctf.adapter.repository.jpa.ctf.entity.CtfEntity;
import ch.coll.ctf.adapter.repository.jpa.ctf.entity.SolveEntity;
import ch.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface JpaSolveEntityRepository extends JpaRepository<SolveEntity, Long> {
    List<SolveEntity> findAll();
    Optional<SolveEntity> findByUserAndCtf(UserEntity user, CtfEntity ctf);
    SolveEntity save(SolveEntity SolveEntity);
}
