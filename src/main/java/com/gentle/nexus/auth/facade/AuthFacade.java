package com.gentle.nexus.auth.facade;

import com.gentle.nexus.auth.dto.SignUpRequestDto;
import com.gentle.nexus.auth.dto.SignUpResponseDto;
import com.gentle.nexus.auth.service.AuthService;
import com.gentle.nexus.common.infra.pass.dto.PassResponseDto;
import com.gentle.nexus.user.dto.UserRegisterDto;
import com.gentle.nexus.user.dto.UserRegisterResultDto;
import com.gentle.nexus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthFacade {

    private final AuthService authService;
    private final UserService userService;

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        // CI 조회
        PassResponseDto passResponseDto = authService.issueCi(signUpRequestDto.getName(), signUpRequestDto.getPhone());

        if (passResponseDto == null || passResponseDto.getSuccess().equals(false)) {
            throw new IllegalArgumentException("CI 조회에 실패했습니다.");
        }

        UserRegisterDto userRegisterDto = UserRegisterDto.builder()
                        .ci(passResponseDto.getCi())
                        .name(passResponseDto.getName())
                        .phone(passResponseDto.getPhone())
                        .password(signUpRequestDto.getPassword())
                        .email(signUpRequestDto.getEmail())
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
