package ch.coll.ctf.adapter.repository.jpa.ctf;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import ch.coll.ctf.domain.ctf.model.CaptureTheFlag;
import ch.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JpaCaptureTheFlagRepository implements CtfRepositoryPort {
  public List<CaptureTheFlag> findAll() {
    return null;
  }

  public Optional<CaptureTheFlag> findById(Long id) {
    return null;
  }

  public CaptureTheFlag save(CaptureTheFlag challenge) {
    return null;
  }

  public void deleteById(Long id) {
  }
}
