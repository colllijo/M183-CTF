package ch.coll.ctf.adapter.repository.jpa.authorisation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ch.coll.ctf.adapter.repository.jpa.authorisation.mapper.PermissionEntityMapper;
import ch.coll.ctf.adapter.repository.jpa.authorisation.mapper.RoleEntityMapper;
import ch.coll.ctf.adapter.repository.jpa.authorisation.service.JpaPermissionEntityRepository;
import ch.coll.ctf.adapter.repository.jpa.authorisation.service.JpaRoleEntityRepository;
import ch.coll.ctf.domain.authorisation.model.Permission;
import ch.coll.ctf.domain.authorisation.model.Role;
import ch.coll.ctf.domain.authorisation.port.out.AuthorisationRepositoryPort;
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

  public List<Role> getRoles() {
    log.info("Getting all roles");

    return roleRepository.findAll().stream().map(roleMapper::mapEntityToModel).toList();
  }

  public Optional<Role> getRoleByName(String name) {
    log.info("Getting role by name - name={}", name);

    return roleRepository.findByName(name).map(roleMapper::mapEntityToModel);
  }

  public Role createRole(Role role) {
    log.info("Creating role - Role={}", role);

    return roleMapper.mapEntityToModel(roleRepository.save(roleMapper.mapModelToEntity(role)));
  }

  public void delteRoleByName(String name) {
    log.info("Deleting role by name - name={}", name);

    roleRepository.findByName(name).ifPresent(roleRepository::delete);
  }

  public void delteRole(Role role) {
    log.info("Deleting role - Role={}", role);

    roleRepository.delete(roleMapper.mapModelToEntity(role));
  }

  public List<Permission> getPermissions() {
    log.info("Getting all permissions");

    return permissionRepository.findAll().stream().map(permissionMapper::mapEntityToModel).toList();
  }

  public Optional<Permission> getPermissionByName(String name) {
    log.info("Getting permission by name - name={}", name);

    return permissionRepository.findByName(name).map(permissionMapper::mapEntityToModel);
  }

  public Permission createPermission(Permission permission) {
    log.info("Creating permission - Permission={}", permission);

    return permissionMapper.mapEntityToModel(permissionRepository.save(permissionMapper.mapModelToEntity(permission)));
  }
}
