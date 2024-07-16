package dominik.biedrzycki.demo.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import dominik.biedrzycki.demo.dtos.BranchDto;
import dominik.biedrzycki.demo.dtos.GithubRepositoriesResponseDto;
import dominik.biedrzycki.demo.mappers.GithubRepositoriesMapper;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.stereotype.Service;

import org.apache.hc.core5.http.HttpEntity;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GithubService {

    private static final String GITHUB_API_URL = "https://api.github.com/users/%s/repos";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    public List<GithubRepositoriesResponseDto> getRepositories(String username) throws Exception {
        String url = String.format(GITHUB_API_URL, username);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(new URI(url));
            request.setHeader("Accept", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getCode();
                if (statusCode == 404) {
                    throw new Exception("User not found: " + username);
                } else if (statusCode != 200) {
                    throw new IOException("Failed to fetch repositories: " + statusCode);
                }

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    JsonNode repos = objectMapper.readTree(entity.getContent());
                    List<GithubRepositoriesResponseDto> repositories = new GithubRepositoriesMapper().mapGetGithubRepositories(repos);

                    for (GithubRepositoriesResponseDto repo : repositories) {
                        List<BranchDto> branches = fetchBranchesForRepository(repo.getOwnerLogin(), repo.getRepositoryName());
                        repo.setBranches(branches);
                    }

                    return repositories;
                }
            }
        }
        return Collections.emptyList();
    }

    private List<BranchDto> fetchBranchesForRepository(String ownerLogin, String repositoryName) throws Exception {
        String branchesUrl = String.format("https://api.github.com/repos/%s/%s/branches", ownerLogin, repositoryName);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(new URI(branchesUrl));
            request.setHeader("Accept", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                int statusCode = response.getCode();
                if (statusCode != 200) {
                    return new ArrayList<>();
                }

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    JsonNode branchesNode = objectMapper.readTree(entity.getContent());
                    List<BranchDto> branches = new ArrayList<>();

                    for (JsonNode branchNode : branchesNode) {
                        String branchName = branchNode.path("name").asText();
                        String lastCommitSHA = branchNode.path("commit").path("sha").asText();
                        branches.add(new BranchDto(branchName, lastCommitSHA));
                    }
                    return branches;
                }
            }
        }
        return new ArrayList<>();
    }
}
