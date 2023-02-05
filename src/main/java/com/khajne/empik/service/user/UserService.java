package com.khajne.empik.service.user;

import com.khajne.empik.controller.model.UserResponse;
import com.khajne.empik.repository.UserRepository;
import com.khajne.empik.repository.model.UserEntity;
import com.khajne.empik.service.user.model.GitHubUser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;
    private final GitHubClient gitHubClient;
    private final RequestCounterService requestCounterService;

    public UserResponse fetchGitHubUserByLogin(final String login) {

        requestCounterService.upsertUser(login);
        var gitHubUser = gitHubClient.getUserByLogin(login);
        BigDecimal calculations = new EquationCalculator().calculate(gitHubUser.followers(), gitHubUser.publicRepos());

        return userMapper.toUserResponse(gitHubUser, calculations);
    }
}
