package ch.coll.ctf.domain.ctf.model;

import java.time.Instant;

import ch.coll.ctf.domain.user.model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Solve {
    @NotBlank
    @Column(unique = true)
    private Ctf ctf;
    private User solver;
    @NotNull
    private Integer points;
    private Instant timestamp;
    private Integer rank;
}
