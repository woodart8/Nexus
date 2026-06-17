package com.gentle.nexus.auth.service;

import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
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

        PassResponseDto passResponseDto = passClient.requestCi(passRequestDto);
        if (passResponseDto == null || passResponseDto.getSuccess().equals(false)) {
            throw new BusinessException(ErrorCode.VERIFICATION_FAILED);
        }
        return passResponseDto;
    }

}
