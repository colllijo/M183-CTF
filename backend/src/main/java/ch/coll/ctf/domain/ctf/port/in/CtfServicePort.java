package ch.coll.ctf.domain.ctf.port.in;

import ch.coll.ctf.domain.ctf.model.Ctf;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CtfServicePort {

     List<Ctf> getAllCtfs();

     Ctf getCtfByName(String name);

     Ctf createCtf(Ctf ctf, MultipartFile file);

     Ctf updateCtf(String name, Ctf ctf);

     void deleteCtf(String name);

}