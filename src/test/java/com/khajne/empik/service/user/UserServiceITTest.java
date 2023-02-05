package com.khajne.empik.service.user;

import com.khajne.empik.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.khajne.empik.service.user.ModelHelper.gitHubUser;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@SpringBootTest
class UserServiceITTest {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @MockBean
    private GitHubClient gitHubClient;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldFetchUserFromGitHub() {
        //given
        final var githubUser = gitHubUser();
        given(gitHubClient.getUserByLogin(githubUser.login())).willReturn(githubUser);

        //when
        var result = userService.fetchGitHubUserByLogin(githubUser.login());

        //then
        final var resultEntityOpt = userRepository.findById(githubUser.login());
        assertTrue(resultEntityOpt.isPresent());
        assertEquals(githubUser.login(), result.getLogin());
    }

}