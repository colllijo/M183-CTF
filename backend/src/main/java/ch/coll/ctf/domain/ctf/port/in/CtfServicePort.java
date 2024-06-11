package ch.coll.ctf.domain.ctf.port.in;

import java.util.List;

import ch.coll.ctf.domain.ctf.model.CaptureTheFlag;

public interface CtfServicePort {


    public List<CaptureTheFlag> getAllChallenges();
    public CaptureTheFlag getChallengeById(Long id);
    public CaptureTheFlag createChallenge(CaptureTheFlag challenge);
    //public CaptureTheFlag updateChallenge(Long id, CaptureTheFlag challenge);

    public CaptureTheFlag updateChallenge(Long id, CaptureTheFlag ctf);

    public void deleteChallenge(Long id);

}
