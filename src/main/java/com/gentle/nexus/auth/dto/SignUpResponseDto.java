package com.gentle.nexus.auth.dto;

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
}
