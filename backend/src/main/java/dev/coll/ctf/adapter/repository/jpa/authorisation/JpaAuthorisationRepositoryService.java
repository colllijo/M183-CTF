package dev.coll.ctf.adapter.repository.jpa.authorisation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.PermissionEntity;
import dev.coll.ctf.adapter.repository.jpa.authorisation.entity.RoleEntity;
import dev.coll.ctf.adapter.repository.jpa.authorisation.mapper.PermissionEntityMapper;
import dev.coll.ctf.adapter.repository.jpa.authorisation.mapper.RoleEntityMapper;
import dev.coll.ctf.adapter.repository.jpa.authorisation.service.JpaPermissionEntityRepository;
import dev.coll.ctf.adapter.repository.jpa.authorisation.service.JpaRoleEntityRepository;
import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.iam.model.exception.PermissionNotFoundException;
import dev.coll.ctf.domain.iam.model.exception.RoleNotFoundException;
import dev.coll.ctf.domain.iam.port.out.AuthorisationRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaAuthorisationRepositoryService implements AuthorisationRepositoryPort {
  private final JpaRoleEntityRepository roleRepository;
  private final JpaPermissionEntityRepository permissionRepository;

  private final RoleEntityMapper roleMapper;
  private final PermissionEntityMapper permissionMapper;

  @Override
  public List<Role> getRoles() {
    log.info("Getting all roles");

    return roleRepository.findAll().stream().map(roleMapper::mapEntityToModel).toList();
  }

  @Override
  public Optional<Role> getRoleById(Long id) {
    return roleRepository.findById(id).map(roleMapper::mapEntityToModel);
  }

  @Override
  public Optional<Role> getRoleByName(String name) {
    log.info("Getting role by name - name={}", name);

    return roleRepository.findByName(name).map(roleMapper::mapEntityToModel);
  }

  @Override
  public Role createRole(Role role) {
    log.info("Creating role - Role={}", role);

    return roleMapper.mapEntityToModel(roleRepository.save(roleMapper.mapModelToEntity(role)));
  }

  @Override
  public Role updateRole(Role role) {
    log.info("Updating role - Role={}", role);

    RoleEntity roleEntity = roleMapper.mapModelToEntity(role);
    roleEntity.getPermissions().forEach(permission -> permission.getRoles().add(roleEntity));

    RoleEntity updateRole = roleRepository.findByName(role.getName()).orElseThrow(() -> new RoleNotFoundException(role.getName()));
    updateRole.setPermissions(roleEntity.getPermissions());

    return roleMapper.mapEntityToModel(roleRepository.save(updateRole));
  }

  @Override
  public void deleteRoleByName(String name) {
    log.info("Deleting role by name - name={}", name);

    roleRepository.findByName(name).ifPresent(roleRepository::delete);
  }

  @Override
  public void deleteRole(Role role) {
    log.info("Deleting role - Role={}", role);

    roleRepository.delete(roleMapper.mapModelToEntity(role));
  }

  @Override
  public List<Permission> getPermissions() {
    log.info("Getting all permissions");

    return permissionRepository.findAll().stream().map(permissionMapper::mapEntityToModel).toList();
  }

  @Override
  public Optional<Permission> getPermissionById(Long id) {
    return permissionRepository.findById(id).map(permissionMapper::mapEntityToModel);
  }

  @Override
  public Optional<Permission> getPermissionByName(String name) {
    log.info("Getting permission by name - name={}", name);

    return permissionRepository.findByName(name).map(permissionMapper::mapEntityToModel);
  }

  @Override
  public Permission createPermission(Permission permission) {
    log.info("Creating permission - Permission={}", permission);

    return permissionMapper.mapEntityToModel(permissionRepository.save(permissionMapper.mapModelToEntity(permission)));
  }

  @Override
  public Permission updatePermission(Permission permission) {
    log.info("Updating permission - Permission={}", permission);

    PermissionEntity permissionEntity = permissionMapper.mapModelToEntity(permission);
    permissionEntity.getRoles().forEach(role -> role.getPermissions().add(permissionEntity));

    PermissionEntity updatePermission = permissionRepository.findByName(permission.getName()).orElseThrow(() -> new PermissionNotFoundException(permission.getName()));
    updatePermission.setRoles(permissionEntity.getRoles());

    return permissionMapper.mapEntityToModel(permissionRepository.save(updatePermission));
  }

  @Override
  public void deletePermissionByName(String name) {
    log.info("Deleting permission by name - name={}", name);

    permissionRepository.findByName(name).ifPresent(permissionRepository::delete);
  }

  @Override
  public void deletePermission(Permission permission) {
    log.info("Deleting permission - Permission={}", permission);

    permissionRepository.delete(permissionMapper.mapModelToEntity(permission));
  }
}
