package ch.coll.ctf.adapter.repository.jpa.ctf.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.coll.ctf.adapter.repository.jpa.ctf.entity.SolveEntity;

public interface JpaSolveEntityRepository extends JpaRepository<SolveEntity, Long> {
  List<SolveEntity> findAll();
}
