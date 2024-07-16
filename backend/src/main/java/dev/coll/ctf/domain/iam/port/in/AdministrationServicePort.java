package dev.coll.ctf.domain.iam.port.in;

import java.util.List;
import java.util.Optional;

import org.springframework.security.access.prepost.PreAuthorize;

import dev.coll.ctf.domain.iam.model.authorisation.Permission;
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
  @PreAuthorize("hasAuthority('READ_USERS')")
  public List<User> getUsers();

  /**
   * @param username
   * @return
   */
  @PreAuthorize("hasAuthority('READ_USER')")
  public Optional<User> getUserByUsername(String username);

  /**
   * @param username
   * @param roleName
   * @return
   */
  @PreAuthorize("hasAuthority('MODIFY_USER_ROLES')")
  public User addRoleToUser(String username, String roleName);

  /**
   * @param username
   * @param roleName
   * @return
   */
  @PreAuthorize("hasAuthority('MODIFY_USER_ROLES')")
  public User removeRoleFromUser(String username, String roleName);

  /**
   * @return
   */
  @PreAuthorize("hasAuthority('READ_ROLES')")
  public List<Role> getRoles();

  /**
   * @param name
   * @return
   */
  @PreAuthorize("hasAuthority('READ_ROLES')")
  public Optional<Role> getRoleByName(String name);

  /**
   * @return
   */
  @PreAuthorize("hasAuthority('READ_PERMISSIONS')")
  public List<Permission> getPermissions();
}
