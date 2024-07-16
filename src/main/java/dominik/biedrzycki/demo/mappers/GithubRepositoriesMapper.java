package dominik.biedrzycki.demo.mappers;

import com.fasterxml.jackson.databind.JsonNode;
import dominik.biedrzycki.demo.dtos.BranchDto;
import dominik.biedrzycki.demo.dtos.GithubRepositoriesResponseDto;

import java.util.ArrayList;
import java.util.List;

public class GithubRepositoriesMapper {
    public List<GithubRepositoriesResponseDto> mapGetGithubRepositories(JsonNode nodes) {
        List<GithubRepositoriesResponseDto> repositories = new ArrayList<>();

        for (JsonNode repoNode : nodes) {
            String repositoryName = repoNode.path("name").asText();
            String ownerLogin = repoNode.path("owner").path("login").asText();

            List<BranchDto> branches = new ArrayList<>();

            repositories.add(new GithubRepositoriesResponseDto(repositoryName, ownerLogin, branches));
        }

        return repositories;
    }
}
