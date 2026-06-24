package com.gentle.nexus.user.controller;

import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
import com.gentle.nexus.common.utils.SecurityUtil;
import com.gentle.nexus.user.dto.UserPasswordDto;
import com.gentle.nexus.user.dto.UserProfileDto;
import com.gentle.nexus.user.facade.UserFacade;
import com.gentle.nexus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;
    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getUserProfile(
            Authentication authentication
    ) {
        Long userId = SecurityUtil.getCurrentUserId(authentication);
        UserProfileDto userProfileDto = userService.getUserProfile(userId);
        return ResponseEntity.ok(userProfileDto);
    }

    @PatchMapping("/me")
    public ResponseEntity<String> updateUserProfile(
            Authentication authentication,
            @RequestBody UserProfileDto userProfileDto
    ) {
        Long userId = SecurityUtil.getCurrentUserId(authentication);
        userFacade.updateUserProfile(userId, userProfileDto);
        return ResponseEntity.ok("회원정보 수정 성공");
    }

    @PatchMapping("/me/password")
    public ResponseEntity<String> updateUserPassword(
            Authentication authentication,
            @RequestBody UserPasswordDto password
    ) {
        Long userId = SecurityUtil.getCurrentUserId(authentication);
        userService.modifyUserPassword(userId, password);
        return ResponseEntity.ok("비밀번호 변경 성공");
    }

    @DeleteMapping("/me")
    public ResponseEntity<String> deleteUser(
            Authentication authentication
    ) {
        Long userId = SecurityUtil.getCurrentUserId(authentication);
        userService.deleteUser(userId);
        return ResponseEntity.ok("회원 탈퇴 처리 완료");
    }

}
