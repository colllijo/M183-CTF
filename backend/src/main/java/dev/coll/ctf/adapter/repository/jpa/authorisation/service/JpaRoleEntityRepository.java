package dev.coll.ctf.adapter.repository.jpa.authorisation.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.RoleEntity;

public interface JpaRoleEntityRepository extends JpaRepository<RoleEntity, Long> {
  public Optional<RoleEntity> findByName(String name);
}
