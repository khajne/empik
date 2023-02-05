package com.khajne.empik.service.user;

import com.khajne.empik.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static com.khajne.empik.service.user.ModelHelper.USER_LOGIN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class RequestCounterServiceITTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RequestCounterService requestCounterService;


    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void shouldCreateUserInDb() {
        //given
        //when
        requestCounterService.upsertUser(USER_LOGIN);

        //then
        final var resultEntityOpt = userRepository.findById(USER_LOGIN);
        assertTrue(resultEntityOpt.isPresent());
        assertEquals(1, resultEntityOpt.get().getRequestCount());
    }

    @Test
    void shouldIncrementCounterForUserInDb() {
        //given
        requestCounterService.upsertUser(USER_LOGIN);

        //when
        requestCounterService.upsertUser(USER_LOGIN);

        //then
        final var resultEntityOpt = userRepository.findById(USER_LOGIN);
        assertTrue(resultEntityOpt.isPresent());
        assertEquals(2, resultEntityOpt.get().getRequestCount());
    }

}