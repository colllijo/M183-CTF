package dev.coll.ctf.domain.iam.port.out;

import java.util.List;
import java.util.Optional;

import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import dev.coll.ctf.domain.iam.model.authorisation.Role;

/**
 * @author Liam, Metzger
 * @version 1.0
 */
public interface AuthorisationRepositoryPort {
  /**
   * @return
   */
  public List<Role> getRoles();

  /**
   * @param id
   * @return
   */
  public Optional<Role> getRoleById(Long id);

  /**
   * @param name
   * @return
   */
  public Optional<Role> getRoleByName(String name);

  /**
   * @param role
   * @return
   */
  public Role createRole(Role role);

  /**
   * @param role
   * @return
   */
  public Role updateRole(Role role);

  /**
   * @param name
   */
  public void deleteRoleByName(String name);

  /**
   * @param role
   */
  public void deleteRole(Role role);

  /**
   * @return
   */
  public List<Permission> getPermissions();

  /**
   * @param id
   * @return
   */
  public Optional<Permission> getPermissionById(Long id);

  /**
   * @param name
   * @return
   */
  public Optional<Permission> getPermissionByName(String name);

  /**
   * @param permission
   * @return
   */
  public Permission createPermission(Permission permission);

  /**
   * @param permission
   * @return
   */
  public Permission updatePermission(Permission permission);

  /**
   * @param name
   */
  public void deletePermissionByName(String name);

  /**
   * @param permission
   */
  public void deletePermission(Permission permission);
}
