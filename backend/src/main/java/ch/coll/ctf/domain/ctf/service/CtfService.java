package ch.coll.ctf.domain.ctf.service;

import ch.coll.ctf.domain.ctf.model.CaptureTheFlag;
import ch.coll.ctf.domain.ctf.port.in.CtfServicePort;
import ch.coll.ctf.domain.ctf.port.out.CtfRepositoryPort;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CtfService implements CtfServicePort {
  private final CtfRepositoryPort ctfRepositoryPort;

  @Override
  public List<CaptureTheFlag> getAllChallenges() {
    return ctfRepositoryPort.findAll();
  }

  @Override
  public CaptureTheFlag getChallengeById(Long id) {
    return ctfRepositoryPort.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Challenge with id " + id + " not found"));
  }

  @Override
  public CaptureTheFlag createChallenge(CaptureTheFlag challenge) {
    return ctfRepositoryPort.save(challenge);
  }

  @Override
  public CaptureTheFlag updateChallenge(Long id, CaptureTheFlag ctf) {
    return ctfRepositoryPort.save(ctf);
  }

  @Override
  public void deleteChallenge(Long id) {
    ctfRepositoryPort.deleteById(id);
  }
}
