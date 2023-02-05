package com.khajne.empik.service.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.khajne.empik.service.user.ModelHelper.*;

class UserMapperTest {

    private final UserMapper userMapper = new UserMapperImpl();

    @Test
    void shouldMapToUserResponse() {
        //given
        final var gitHubUser = gitHubUser();
        //when
        var result = userMapper.toUserResponse(gitHubUser, USER_CALCULATIONS);
        //then
        Assertions.assertEquals(USER_GITHUB_ID, result.getId());
        Assertions.assertEquals(USER_LOGIN, result.getLogin());
        Assertions.assertEquals(USER_NAME, result.getName());
        Assertions.assertEquals(USER_TYPE, result.getType());
        Assertions.assertEquals(USER_CREATED_AT, result.getCreatedAt());
        Assertions.assertEquals(USER_AVATAR_URL, result.getAvatarUrl());
        Assertions.assertEquals(USER_CALCULATIONS, result.getCalculations());
    }
}