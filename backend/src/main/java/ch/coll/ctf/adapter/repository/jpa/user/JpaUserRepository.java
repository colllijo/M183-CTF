package ch.coll.ctf.adapter.repository.jpa.user;

import java.util.Optional;

import org.springframework.stereotype.Service;

import ch.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import ch.coll.ctf.adapter.repository.jpa.user.mapper.UserEntityMapper;
import ch.coll.ctf.adapter.repository.jpa.user.service.JpaUserEntityRepository;
import ch.coll.ctf.domain.user.model.User;
import ch.coll.ctf.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepositoryPort {
  private final JpaUserEntityRepository userRepository;
  private final UserEntityMapper userMapper;

  public Optional<User> getUserById(Long id) {
    log.info("Getting user by id - id={}", id);

    return userRepository.findById(id).map(userMapper::mapEntityToModel);
  }

  public Optional<User> getUserByUsername(String username) {
    log.info("Getting user by username - username={}", username);

    return userRepository.findByUsername(username).map(userMapper::mapEntityToModel);
  }

  public User createUser(User user) {
    log.info("Creating user - User={}", user);

    UserEntity userEntity = userMapper.mapModelToEntity(user);
    userEntity.getRoles().forEach(role -> role.getUsers().add(userEntity));

    return userMapper.mapEntityToModel(userRepository.save(userEntity));
  }
}
