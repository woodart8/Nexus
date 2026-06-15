package com.gentle.nexus.common.infra.pass.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PassRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

}
