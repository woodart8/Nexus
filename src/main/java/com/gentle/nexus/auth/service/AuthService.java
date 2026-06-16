package com.gentle.nexus.auth.service;

import com.gentle.nexus.common.infra.pass.PassClient;
import com.gentle.nexus.common.infra.pass.dto.PassRequestDto;
import com.gentle.nexus.common.infra.pass.dto.PassResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PassClient passClient;

    public PassResponseDto issueCi(String name, String phone) {
        PassRequestDto passRequestDto = PassRequestDto.builder()
                .name(name)
                .phone(phone)
                .build();
        return passClient.requestCi(passRequestDto);
    }

}
