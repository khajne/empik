package com.khajne.empik.controller;

import com.khajne.empik.controller.model.UserResponse;
import com.khajne.empik.exception.FailedToFetchGitHubUserException;
import com.khajne.empik.exception.GitHubUserNotFoundException;
import com.khajne.empik.exception.IncorrectDataException;
import com.khajne.empik.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static com.khajne.empik.service.user.ModelHelper.USER_LOGIN;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserRestController.class)
class UserRestControllerTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getUserByLoginShouldReturnOk() throws Exception {
        //given
        given(userService.fetchGitHubUserByLogin(USER_LOGIN)).willReturn(new UserResponse());

        //when
        //then
        mockMvc.perform(get("/users/{login}", USER_LOGIN)).andExpect(status().isOk());
    }

    @Test
    void getUserByLoginShouldReturnNotFoundWhenUserNotFoundOnGitHub() throws Exception {
        //given
        given(userService.fetchGitHubUserByLogin(USER_LOGIN)).willThrow(new GitHubUserNotFoundException());

        //when
        //then
        mockMvc.perform(get("/users/{login}", USER_LOGIN)).andExpect(status().isNotFound());
    }

    @Test
    void getUserByLoginShouldReturnBadRequestWhenCantPerformCalculation() throws Exception {
        //given
        given(userService.fetchGitHubUserByLogin(USER_LOGIN)).willThrow(new IncorrectDataException("msg"));

        //when
        //then
        mockMvc.perform(get("/users/{login}", USER_LOGIN)).andExpect(status().isBadRequest());
    }

    @Test
    void getUserByLoginShouldReturnInternalServerErrorWhenResponseFromGithubIsBroken() throws Exception {
        //given
        given(userService.fetchGitHubUserByLogin(USER_LOGIN)).willThrow(new FailedToFetchGitHubUserException("login"));

        //when
        //then
        mockMvc.perform(get("/users/{login}", USER_LOGIN)).andExpect(status().isInternalServerError());
    }
}