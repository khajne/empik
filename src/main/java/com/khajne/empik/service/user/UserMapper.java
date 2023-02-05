package com.khajne.empik.service.user;

import com.khajne.empik.controller.model.UserResponse;
import com.khajne.empik.service.user.model.GitHubUser;
import org.mapstruct.Mapper;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toUserResponse(GitHubUser gitHubUser, BigDecimal calculations);
}
