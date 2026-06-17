package com.gentle.nexus.common.infra.pass.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PassResponseDto {

    private String ci;
    private String name;
    private String phone;
    private Boolean success;

}
