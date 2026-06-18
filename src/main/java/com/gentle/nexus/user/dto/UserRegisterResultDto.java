package com.gentle.nexus.user.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.gentle.nexus.user.domain.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserRegisterResultDto {
    private Long id;
    private String name;
    private String phone;
    private String email;

    public static UserRegisterResultDto from(User user) {
        return new UserRegisterResultDto(
                user.getId(), user.getName(), user.getPhone(), user.getEmail()
        );
    }
}
