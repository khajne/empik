package com.khajne.empik.service.user;

import com.khajne.empik.service.user.model.GitHubUser;

import java.math.BigDecimal;

public class ModelHelper {

    public static final Long USER_GITHUB_ID = 10L;
    public static final String USER_LOGIN = "LOGIN";
    public static final String USER_NAME = "NAME";
    public static final String USER_TYPE = "TYPE";
    public static final String USER_AVATAR_URL = "www.url.com" ;
    public static final String USER_CREATED_AT = "2008-01-14T04:33:35Z";
    public static final Integer USER_FOLLOWERS = 10;
    public static final Integer USER_PUBLIC_REPOS = 3;
    public static final BigDecimal USER_CALCULATIONS = BigDecimal.valueOf(3);

    public static GitHubUser gitHubUser(){
        return new GitHubUser(USER_GITHUB_ID, USER_LOGIN, USER_NAME, USER_TYPE, USER_AVATAR_URL, USER_CREATED_AT, USER_FOLLOWERS, USER_PUBLIC_REPOS);
    }
}
