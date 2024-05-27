package ch.coll.ctf.adapter.repository.jpa.ctf;

import org.springframework.stereotype.Service;

import ch.coll.ctf.adapter.repository.jpa.ctf.service.JpaCaptureTheFlagEntityRepository;
import ch.coll.ctf.adapter.repository.jpa.ctf.service.JpaSolveEntityRepository;
import ch.coll.ctf.domain.ctf.port.out.CaptureTheFlagRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class JpaCaptureTheFlagRepository implements CaptureTheFlagRepositoryPort {
  private final JpaCaptureTheFlagEntityRepository captureTheFlagRepository;
  private final JpaSolveEntityRepository solveRepository;
}
