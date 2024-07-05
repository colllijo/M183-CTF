package dev.coll.ctf.adapter.repository.jpa.authorisation.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.PermissionEntity;

public interface JpaPermissionEntityRepository extends JpaRepository<PermissionEntity, Long> {
  public Optional<PermissionEntity> findByName(String name);
}
