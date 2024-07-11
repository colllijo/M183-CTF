package dev.coll.ctf.adapter.repository.jpa.authorisation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.RoleEntity;
import dev.coll.ctf.adapter.repository.jpa.authorisation.mapper.RoleEntityMapper;
import dev.coll.ctf.adapter.repository.jpa.authorisation.service.JpaRoleEntityRepository;
import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.iam.model.exception.RoleNotFoundException;
import dev.coll.ctf.domain.iam.port.out.AuthorisationRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaAuthorisationRepositoryService implements AuthorisationRepositoryPort {
  private final JpaRoleEntityRepository roleRepository;
  private final RoleEntityMapper roleMapper;

  @Override
  public List<Role> getRoles() {
    return roleRepository.findAll().stream().map(roleMapper::mapEntityToModel).toList();
  }

  @Override
  public Optional<Role> getRoleById(Long id) {
    return roleRepository.findById(id).map(roleMapper::mapEntityToModel);
  }

  @Override
  public Optional<Role> getRoleByName(String name) {
    return roleRepository.findByName(name).map(roleMapper::mapEntityToModel);
  }

  @Override
  public Role createRole(Role role) {
    return roleMapper.mapEntityToModel(roleRepository.save(roleMapper.mapModelToEntity(role)));
  }

  @Override
  public Role updateRole(Role role) {
    RoleEntity roleEntity = roleMapper.mapModelToEntity(role);

    RoleEntity updateRole = roleRepository.findByName(role.getName()).orElseThrow(() -> new RoleNotFoundException(role.getName()));
    updateRole.setPermissions(roleEntity.getPermissions());

    return roleMapper.mapEntityToModel(roleRepository.save(updateRole));
  }

  @Override
  public void deleteRoleByName(String name) {
    roleRepository.findByName(name).ifPresent(roleRepository::delete);
  }

  @Override
  public void deleteRole(Role role) {
    roleRepository.delete(roleMapper.mapModelToEntity(role));
  }
}
