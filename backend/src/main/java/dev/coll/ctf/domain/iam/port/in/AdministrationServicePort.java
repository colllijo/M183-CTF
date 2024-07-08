package dev.coll.ctf.domain.iam.port.in;

import java.util.List;

import dev.coll.ctf.domain.iam.model.annotation.RequirePermission;
import dev.coll.ctf.domain.iam.model.authorisation.Permission;
import dev.coll.ctf.domain.iam.model.authorisation.Permissions;
import dev.coll.ctf.domain.iam.model.authorisation.Role;
import dev.coll.ctf.domain.user.model.User;

/**
 * @author Liam, Metzger
 * @version 1.0
 */
public interface AdministrationServicePort {
  /**
   * @return
   */
  @RequirePermission(Permissions.READ_USERS)
  public List<User> getUsers();

  /**
   * @param username
   * @return
   */
  @RequirePermission(Permissions.READ_USERS)
  public User getUserByUsername(String username);

  /**
   * @param username
   * @param roleName
   * @return
   */
  @RequirePermission(Permissions.MODIFY_USER_ROLES)
  public User addRoleToUser(String username, String roleName);

  /**
   * @param username
   * @param roleName
   * @return
   */
  @RequirePermission(Permissions.MODIFY_USER_ROLES)
  public User removeRoleFromUser(String username, String roleName);

  /**
   * @return
   */
  @RequirePermission(Permissions.READ_ROLES)
  public List<Role> getRoles();

  /**
   * @param name
   * @return
   */
  @RequirePermission(Permissions.READ_ROLES)
  public Role getRoleByName(String name);

  /**
   * @return
   */
  @RequirePermission(Permissions.READ_PERMISSIONS)
  public List<Permission> getPermissions();
}
