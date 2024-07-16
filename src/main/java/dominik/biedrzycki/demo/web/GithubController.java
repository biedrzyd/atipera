package dominik.biedrzycki.demo.web;

import dominik.biedrzycki.demo.dtos.GithubRepositoriesResponseDto;
import dominik.biedrzycki.demo.services.GithubService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GithubController {

    private final GithubService githubService;

    public GithubController(GithubService githubService) {
        this.githubService = githubService;
    }

    @GetMapping(path = "/github-repositories", headers = "Accept=application/json")
    public List<GithubRepositoriesResponseDto> getGithubRepositories (@RequestParam(name = "username", required = true) String username) throws Exception {
        return githubService.getRepositories(username);
    }
}
