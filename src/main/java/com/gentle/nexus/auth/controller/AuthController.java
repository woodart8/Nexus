package com.gentle.nexus.auth.controller;

import com.gentle.nexus.auth.dto.*;
import com.gentle.nexus.auth.facade.AuthFacade;
import com.gentle.nexus.auth.service.AuthService;
import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
import com.gentle.nexus.common.jwt.JwtProperties;
import com.gentle.nexus.common.utils.SecurityUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtProperties jwtProperties;
    private final AuthFacade authFacade;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto res = authFacade.signUp(signUpRequestDto);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(
            @RequestBody LoginRequestDto loginRequestDto,
            HttpServletResponse response
    ) {
        TokenDto res = authService.login(loginRequestDto);

        Cookie cookie = new Cookie("refresh_token", res.getRefreshToken());
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(Math.toIntExact(jwtProperties.getRefreshTokenExpiration() / 1000));

        response.addCookie(cookie);

        return ResponseEntity.ok(
                TokenDto.builder().accessToken(res.getAccessToken()).build()
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDto> refresh(
            @CookieValue(name = "refresh_token", required = false) String refreshToken,
            HttpServletResponse response
    ) {

        if (refreshToken == null) {
            throw new BusinessException(ErrorCode.NO_REFRESH_TOKEN);
        }

        TokenDto res = authService.refresh(refreshToken);

        if (res.getRefreshToken() != null) {
            Cookie cookie = new Cookie("refresh_token", res.getRefreshToken());
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(Math.toIntExact(jwtProperties.getRefreshTokenExpiration() / 1000));

            response.addCookie(cookie);
        }

        return ResponseEntity.ok(
                TokenDto.builder().accessToken(res.getAccessToken()).build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            Authentication authentication,
            HttpServletResponse response
    ) {
        Long userId = SecurityUtil.getCurrentUserId(authentication);
        authService.logout(userId);

        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

        return ResponseEntity.ok("로그아웃 성공");
    }


}
