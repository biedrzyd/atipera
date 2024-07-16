package dominik.biedrzycki.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BranchDto {
    private String name;
    private String lastCommitSHA;
}
