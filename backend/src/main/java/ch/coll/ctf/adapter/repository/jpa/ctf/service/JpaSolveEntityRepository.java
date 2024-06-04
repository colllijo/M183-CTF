package ch.coll.ctf.adapter.repository.jpa.ctf.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.coll.ctf.adapter.repository.jpa.ctf.entity.SolveEntity;

public interface JpaSolveEntityRepository extends JpaRepository<SolveEntity, Long> {
}
