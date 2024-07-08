package dev.coll.ctf.domain.user.port.in;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;

import dev.coll.ctf.domain.user.model.User;

/**
 * Interface for user.
 *
 * @author Liam, Metzger
 * @version 1.0
 */
public interface UserServicePort {
  /**
   * @return
   */
  public List<User> getUsers();

  /**
   * @param username
   * @return
   */
  public Optional<User> getUserByUsername(String username);

  /**
   * @param user
   * @return
   */
  public User createUser(User user);

  /**
   * @param user
   * @return
   */
  public User updateUser(User user);

  /**
   * @param user
   * @return
   */
  public List<? extends GrantedAuthority> getAuthorities(User user);
}
