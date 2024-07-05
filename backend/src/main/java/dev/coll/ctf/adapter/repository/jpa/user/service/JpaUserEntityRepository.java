package dev.coll.ctf.adapter.repository.jpa.user.service;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;

public interface JpaUserEntityRepository extends JpaRepository<UserEntity, Long> {
  public Optional<UserEntity> findByUsername(String username);
}
