package ch.coll.ctf.domain.ctf.port.in;

import java.util.List;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.multipart.MultipartFile;

import ch.coll.ctf.domain.ctf.model.Ctf;

public interface CtfServicePort {
  List<Ctf> getAllCtfs();

  Ctf getCtfByName(String name);

  ByteArrayResource downloadFile(String filePath);

  Ctf createCtf(Ctf ctf, MultipartFile attachment);

  Ctf updateCtf(String name, Ctf ctf);

  void deleteCtf(String name);
}
