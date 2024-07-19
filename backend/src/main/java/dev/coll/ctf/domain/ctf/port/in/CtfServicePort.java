package dev.coll.ctf.domain.ctf.port.in;

import java.util.List;
import java.util.Optional;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.multipart.MultipartFile;

import dev.coll.ctf.domain.ctf.model.Ctf;
import dev.coll.ctf.domain.ctf.model.Solve;

public interface CtfServicePort {
  public List<Ctf> getAllCtfs();

  public Optional<Ctf> getCtfByName(String name);

  @PreAuthorize("isAuthenticated()")
  public ByteArrayResource downloadFile(String filePath);

  @PreAuthorize("hasAuthority('SUBMIT_FLAG')")
  public Solve submitFlag(String ctfName, String flag);

  @PreAuthorize("hasAuthority('CREATE_CHALLENGE')")
  public Ctf createCtf(Ctf ctf, MultipartFile attachment);

  public Ctf updateCtf(String name, Ctf ctf);

  public void deleteCtf(String name);
}
