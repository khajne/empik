package com.khajne.empik.controller.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {

    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private String createdAt;
    private BigDecimal calculations;

}
