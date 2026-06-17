package com.gentle.nexus.common.infra.pass.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PassResponseDto {

    private String ci;
    private String name;
    private String phone;
    private Boolean success;

}
