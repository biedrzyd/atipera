package dominik.biedrzycki.demo.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class GithubRepositoriesResponseDto {
    private String repositoryName;
    private String ownerLogin;
    List<BranchDto> branches;
}
