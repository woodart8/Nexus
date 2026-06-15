package com.gentle.nexus.auth.service;

import com.gentle.nexus.auth.dto.SignUpRequestDto;
import com.gentle.nexus.auth.dto.SignUpResponseDto;
import com.gentle.nexus.common.infra.pass.PassClient;
import com.gentle.nexus.common.infra.pass.dto.PassRequestDto;
import com.gentle.nexus.common.infra.pass.dto.PassResponseDto;
import com.gentle.nexus.user.dto.UserRegisterDto;
import com.gentle.nexus.user.dto.UserRegisterResultDto;
import com.gentle.nexus.user.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class AuthService {

    private final PassClient passClient;
    private final UserService userService;

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        // CI 조회
        PassRequestDto passRequestDto = PassRequestDto.builder()
                .name(signUpRequestDto.getName())
                .phone(signUpRequestDto.getPhone())
                .build();
        PassResponseDto passResponseDto = passClient.requestCi(passRequestDto);

        if (passResponseDto == null || passResponseDto.getSuccess().equals(false)) {
            log.error("CI 조회에 실패했습니다.");
            throw new IllegalArgumentException("CI 조회에 실패했습니다.");
        }

        // 회원 등록
        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                        .ci(passResponseDto.getCi())
                        .password(signUpRequestDto.getPassword())
                        .name(signUpRequestDto.getName())
                        .email(signUpRequestDto.getEmail())
                        .phone(signUpRequestDto.getPhone())
                        .build();

        UserRegisterResultDto userRegisterResultDto = userService.register(userRegisterDto);

        // 결과 리턴
        return SignUpResponseDto.builder()
                .id(userRegisterResultDto.getId())
                .name(userRegisterResultDto.getName())
                .email(userRegisterResultDto.getEmail())
                .phone(userRegisterResultDto.getPhone())
                .success(true)
                .build();
    }

}
