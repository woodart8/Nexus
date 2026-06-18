package com.gentle.nexus.auth.service;

import com.gentle.nexus.auth.dto.LoginRequestDto;
import com.gentle.nexus.auth.dto.TokenDto;
import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
import com.gentle.nexus.common.infra.pass.PassClient;
import com.gentle.nexus.common.infra.pass.dto.PassRequestDto;
import com.gentle.nexus.common.infra.pass.dto.PassResponseDto;
import com.gentle.nexus.common.jwt.JwtProvider;
import com.gentle.nexus.user.domain.User;
import com.gentle.nexus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final PassClient passClient;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

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

    public TokenDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByLoginId(loginRequestDto.getLoginId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            throw new BusinessException(ErrorCode.LOGIN_FAILED);
        }

        String accessToken = jwtProvider.createAccessToken(user.getId());
        String refreshToken = jwtProvider.createRefreshToken(user.getId());

        refreshTokenService.save(user.getId(), refreshToken);

        return TokenDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public TokenDto refresh(String refreshToken) {
        Long userId = jwtProvider.getUserId(refreshToken);

        String savedToken = refreshTokenService.get(userId);

        if (!refreshToken.equals(savedToken)) {
            throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
        }

        String newAccess = jwtProvider.createAccessToken(userId);
        String newRefresh = jwtProvider.createRefreshToken(userId);

        refreshTokenService.rotate(userId, newRefresh);

        return TokenDto.builder()
                .accessToken(newAccess)
                .refreshToken(newRefresh)
                .build();
    }

}
