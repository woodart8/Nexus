package com.gentle.nexus.user.controller;

import com.gentle.nexus.user.dto.UserPasswordDto;
import com.gentle.nexus.user.dto.UserProfileDto;
import com.gentle.nexus.user.facade.UserFacade;
import com.gentle.nexus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserProfileDto> getUserProfile(@PathVariable Long id) {
        UserProfileDto userProfileDto = userService.getUserProfile(id);
        return ResponseEntity.ok(userProfileDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateUserProfile(
            @PathVariable(name = "id") Long id,
            @RequestBody UserProfileDto userProfileDto
    ) {
        userFacade.updateUserProfile(id, userProfileDto);
        return ResponseEntity.ok("회원정보 수정 성공");
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<String> updateUserPassword(
            @PathVariable(name = "id") Long id,
            @RequestBody UserPasswordDto password
    ) {
        userService.modifyUserPassword(id, password);
        return ResponseEntity.ok("비밀번호 변경 성공");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable(name = "id") Long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("회원 탈퇴 처리 완료");
    }

}
