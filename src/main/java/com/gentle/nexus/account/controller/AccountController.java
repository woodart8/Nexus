package com.gentle.nexus.account.controller;

import com.gentle.nexus.account.dto.AccountDto;
import com.gentle.nexus.account.dto.ChangeAccountPasswordDto;
import com.gentle.nexus.account.dto.CreateAccountRequestDto;
import com.gentle.nexus.account.service.AccountService;
import com.gentle.nexus.common.utils.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseEntity<AccountDto> createAccount(
            Authentication authentication,
            @RequestBody CreateAccountRequestDto req
    ) {
        Long userId = SecurityUtil.getCurrentUserId(authentication);
        AccountDto newAccount = accountService.createAccount(userId, req);
        return ResponseEntity.ok(newAccount);
    }

    @PatchMapping("/password")
    public ResponseEntity<String> updateAccountPassword(
            Authentication authentication,
            @RequestBody ChangeAccountPasswordDto req
    ) {
        Long userId = SecurityUtil.getCurrentUserId(authentication);
        accountService.modifyAccountPassword(userId, req);
        return ResponseEntity.ok("계좌 비밀번호 변경 성공");
    }

}
