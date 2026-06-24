package com.gentle.nexus.account.controller;

import com.gentle.nexus.account.dto.AccountDto;
import com.gentle.nexus.account.dto.CreateAccountRequestDto;
import com.gentle.nexus.account.service.AccountService;
import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new BusinessException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
        Long userId = (Long)authentication.getPrincipal();
        AccountDto newAccount = accountService.createAccount(userId, req);
        return ResponseEntity.ok(newAccount);
    }

}
