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

        UserRegisterDto userRegisterDto = buildUserRegister(passResponseDto.getCi(), signUpRequestDto);

        // 회원 등록
        UserRegisterResultDto userRegisterResultDto = userService.register(userRegisterDto);

        // 결과 리턴
        return SignUpResponseDto.from(userRegisterResultDto);
    }

    private UserRegisterDto buildUserRegister(String ci, SignUpRequestDto dto) {
        return UserRegisterDto.builder()
                .ci(ci)
                .name(dto.getName())
                .phone(dto.getPhone())
                .loginId(dto.getLoginId())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .build();
    }
}
