package com.khajne.empik.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khajne.empik.exception.FailedToFetchGitHubUserException;
import com.khajne.empik.exception.GitHubUserNotFoundException;
import com.khajne.empik.service.user.model.GitHubUser;
import com.khajne.empik.tools.WebClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class GitHubClient {

    private final WebClient webClient;
    private final ObjectMapper objectMapper;
    @Value("${empik.github-url}")
    private String githubUrl;

    public GitHubUser getUserByLogin(String login) {

        var response = getDataFromGitHub(login);
        performResponseValidation(response);
        var responseBodyString = getResponseBodyString(response);

        return mapToGithubUser(responseBodyString);
    }

    private Response getDataFromGitHub(String login) {
        return webClient.sendGetRequest(githubUrl + login)
                .orElseThrow(() -> new FailedToFetchGitHubUserException("Something went wrong, response is missing!"));
    }

    private void performResponseValidation(final Response response) {
        if (response.code() == HttpStatus.NOT_FOUND.value()) {
            throw new GitHubUserNotFoundException();
        }
        if (response.code() != HttpStatus.OK.value()) {
            throw new FailedToFetchGitHubUserException("Random error while fetching resource from github!");
        }
    }

    private String getResponseBodyString(Response responseOpt) {
        return Try.withResources(responseOpt::body)
                .of(ResponseBody::string)
                .toJavaOptional()
                .orElseThrow(() -> new FailedToFetchGitHubUserException("Can't get body String from response!"));
    }

    private GitHubUser mapToGithubUser(String bodyString) {
        return Try.of(() -> objectMapper.readValue(bodyString, GitHubUser.class))
                .onFailure(ex -> log.error("Corrupted JSON! '{}', can't read value!", bodyString))
                .getOrElseThrow(() -> new FailedToFetchGitHubUserException("Corrupted JSON fetched from GitHub! Can't prepare response!"));
    }
}
