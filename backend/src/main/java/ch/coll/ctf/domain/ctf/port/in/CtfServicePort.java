package ch.coll.ctf.domain.ctf.port.in;

import ch.coll.ctf.domain.ctf.model.CaptureTheFlag;
import ch.coll.ctf.domain.user.model.User;

import java.util.List;
import java.util.Optional;

public interface CtfServicePort {


    public List<CaptureTheFlag> getAllChallenges();
    public CaptureTheFlag getChallengeById(Long id);
    public CaptureTheFlag createChallenge(CaptureTheFlag challenge);
    //public CaptureTheFlag updateChallenge(Long id, CaptureTheFlag challenge);

    public CaptureTheFlag updateChallenge(Long id, CaptureTheFlag ctf);

    public void deleteChallenge(Long id);

}