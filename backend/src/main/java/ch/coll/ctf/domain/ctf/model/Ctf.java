package ch.coll.ctf.domain.ctf.model;

import ch.coll.ctf.domain.user.model.User;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Ctf {
    @NotBlank
    @Column(unique = true)
    private String name;
    private String description;
    private User author;
    @NotBlank
    @Pattern(regexp = "^cctf\\{.*}$", message = "Flag must be in the format cctf{xxx}")
    private String flag;
    @NotEmpty
    private Set<Solve> solves;
}
