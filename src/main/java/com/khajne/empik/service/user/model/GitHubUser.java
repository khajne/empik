package com.khajne.empik.service.user.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public record GitHubUser(Long id, String login, String name, String type, @JsonProperty("avatar_url") String avatarUrl,
                         @JsonProperty("created_at") String createdAt, Integer followers,
                         @JsonProperty("public_repos") Integer publicRepos) {


}
