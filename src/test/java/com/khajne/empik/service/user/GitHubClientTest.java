package com.khajne.empik.service.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.khajne.empik.exception.FailedToFetchGitHubUserException;
import com.khajne.empik.exception.GitHubUserNotFoundException;
import com.khajne.empik.service.user.model.GitHubUser;
import com.khajne.empik.tools.WebClient;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import java.io.IOException;
import java.util.Optional;

import static com.khajne.empik.service.user.ModelHelper.USER_LOGIN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(properties = {
        "empik.github-url=https://randomurl.com",
})
class GitHubClientTest {

    @Value("${empik.github-url}")
    private String url;

    @Mock
    private WebClient webClient;
    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private GitHubClient gitHubClient;


    @Test
    void shouldThrowExceptionWhenResponseIsEmpty() {
        //given
        given(webClient.sendGetRequest(url + USER_LOGIN)).willReturn(Optional.empty());
        final String expectedMessage = "Something went wrong, response is missing!";
        //when
        Exception exception = assertThrows(FailedToFetchGitHubUserException.class, () -> {
            gitHubClient.getUserByLogin(USER_LOGIN);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        //given
        var response = mock(Response.class);
        given(response.code()).willReturn(HttpStatus.NOT_FOUND.value());
        given(webClient.sendGetRequest(url + USER_LOGIN)).willReturn(Optional.of(response));
        //when
        //then
        assertThrows(GitHubUserNotFoundException.class, () -> {
            gitHubClient.getUserByLogin(USER_LOGIN);
        });
    }

    @Test
    void shouldThrowExceptionWhenStatusCodeNotOk() {
        //given
        var response = mock(Response.class);
        given(response.code()).willReturn(HttpStatus.I_AM_A_TEAPOT.value());
        given(webClient.sendGetRequest(url + USER_LOGIN)).willReturn(Optional.of(response));
        final String expectedMessage = "Random error while fetching resource from github!";

        //when
        Exception exception = assertThrows(FailedToFetchGitHubUserException.class, () -> {
            gitHubClient.getUserByLogin(USER_LOGIN);
        });

        //then
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void shouldFetchGitHubUser() throws IOException {
        //given
        var response = mock(Response.class);
        var body = mock(ResponseBody.class);
        var bodyString = "response";
        var gitHubUser = mock(GitHubUser.class);

        given(response.code()).willReturn(HttpStatus.OK.value());
        given(response.body()).willReturn(body);
        given(body.string()).willReturn(bodyString);
        given(objectMapper.readValue(bodyString, GitHubUser.class)).willReturn(gitHubUser);
        given(webClient.sendGetRequest(url + USER_LOGIN)).willReturn(Optional.of(response));

        //when
        var result = gitHubClient.getUserByLogin(USER_LOGIN);

        //then
        assertEquals(gitHubUser, result);
    }
}