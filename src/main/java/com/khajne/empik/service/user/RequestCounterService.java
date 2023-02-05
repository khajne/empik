package com.khajne.empik.service.user;

import com.khajne.empik.repository.UserRepository;
import com.khajne.empik.repository.model.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestCounterService {

    private final UserRepository userRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void upsertUser(final String login) {
        log.info("Obtained request to upsert statistic for user with login {}", login);
        var userOpt = userRepository.findById(login);
        userOpt.ifPresentOrElse(this::incrementCounter, () -> createNewEntity(login));
    }

    private void createNewEntity(String login) {
        userRepository.save(new UserEntity(login, 1));
        log.info("User with login {} has been saved in db!", login);
    }

    private void incrementCounter(UserEntity userEntity) {
        userEntity.setRequestCount(userEntity.getRequestCount() + 1);
        log.info("Counter for user with login {} has been updated!", userEntity.getLogin());
    }
}
