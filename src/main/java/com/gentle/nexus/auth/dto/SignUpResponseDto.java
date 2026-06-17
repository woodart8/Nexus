package com.gentle.nexus.auth.dto;

import com.gentle.nexus.user.dto.UserRegisterResultDto;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignUpResponseDto {
    private Long id;
    private String name;
    private String phone;
    private String email;
    private Boolean success;

    public static SignUpResponseDto from(UserRegisterResultDto user) {
        return SignUpResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .success(true)
                .build();
    }
}
