package com.khajne.empik.controller;

import com.khajne.empik.controller.model.UserResponse;
import com.khajne.empik.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserRestController {

    private final UserService userService;

    @GetMapping("/{login}")
    public UserResponse fetchGitHubUserByLogin(@PathVariable @Valid @NotBlank final String login) {
        return userService.fetchGitHubUserByLogin(login);
    }
}
