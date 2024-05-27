package ch.coll.ctf.adapter.repository.jpa.ctf.service;

import org.springframework.data.jpa.repository.JpaRepository;

import ch.coll.ctf.adapter.repository.jpa.ctf.entity.CaptureTheFlagEntity;

public interface JpaCaptureTheFlagEntityRepository extends JpaRepository<CaptureTheFlagEntity, Long> {
}
