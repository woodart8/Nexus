package com.gentle.nexus.auth.controller;

import com.gentle.nexus.auth.dto.SignUpRequestDto;
import com.gentle.nexus.auth.dto.SignUpResponseDto;
import com.gentle.nexus.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        SignUpResponseDto res = authService.signUp(signUpRequestDto);
        return ResponseEntity.ok(res);
    }

}
