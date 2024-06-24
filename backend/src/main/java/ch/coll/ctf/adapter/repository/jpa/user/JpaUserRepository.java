package ch.coll.ctf.adapter.repository.jpa.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import ch.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import ch.coll.ctf.adapter.repository.jpa.user.mapper.UserEntityMapper;
import ch.coll.ctf.adapter.repository.jpa.user.service.JpaUserEntityRepository;
import ch.coll.ctf.domain.user.exception.UserNotFoundException;
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

  public List<User> getUsers() {
    log.info("Getting users");

    return userRepository.findAll().stream().map(userMapper::mapEntityToModel).collect(Collectors.toList());
  }

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

  public User updateUser(User user) {
    log.info("Updating user - User={}", user);

    UserEntity userEntity = userMapper.mapModelToEntity(user);
    userEntity.getRoles().forEach(role -> role.getUsers().add(userEntity));

    UserEntity updateUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UserNotFoundException(user.getUsername()));
    updateUser.setEmail(userEntity.getEmail());
    updateUser.setRoles(userEntity.getRoles());

    return userMapper.mapEntityToModel(userRepository.save(updateUser));
  }
}
