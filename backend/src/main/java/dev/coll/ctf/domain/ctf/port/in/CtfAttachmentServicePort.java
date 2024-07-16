package dev.coll.ctf.domain.ctf.port.in;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

/*
 * @author Liam, Metzger
 * @version 1.0
 */
public interface CtfAttachmentServicePort {
  /**
   * @param name
   * @param attachment
   * @return
   */
  @PreAuthorize("hasRole('AUTHOR')")
  public String saveFile(String name, MultipartFile attachment);

  /**
   * @param filePath
   * @return
   */
  public ByteArrayResource loadFile(String filePath);
}
