package dev.coll.ctf.adapter.repository.jpa.user;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.coll.ctf.adapter.repository.jpa.user.entity.UserEntity;
import dev.coll.ctf.adapter.repository.jpa.user.mapper.UserEntityMapper;
import dev.coll.ctf.adapter.repository.jpa.user.service.JpaUserEntityRepository;
import dev.coll.ctf.domain.user.model.User;
import dev.coll.ctf.domain.user.model.exception.UserNotFoundException;
import dev.coll.ctf.domain.user.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaUserRepository implements UserRepositoryPort {
  private final JpaUserEntityRepository userRepository;
  private final UserEntityMapper userMapper;

  public List<User> getUsers() {
    return userRepository.findAll().stream().map(userMapper::mapEntityToModel).collect(Collectors.toList());
  }

  public Optional<User> getUserById(Long id) {
    return userRepository.findById(id).map(userMapper::mapEntityToModel);
  }

  public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username).map(userMapper::mapEntityToModel);
  }

  public User createUser(User user) {
    return userMapper.mapEntityToModel(userRepository.save(userMapper.mapModelToEntity(user)));
  }

  public User updateUser(User user) {
    UserEntity userEntity = userMapper.mapModelToEntity(user);

    UserEntity updateUser = userRepository.findByUsername(user.getUsername()).orElseThrow(() -> new UserNotFoundException(user.getUsername()));
    updateUser.setEmail(userEntity.getEmail());
    updateUser.setRoles(userEntity.getRoles());

    return userMapper.mapEntityToModel(userRepository.save(updateUser));
  }
}
