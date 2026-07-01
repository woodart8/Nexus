package com.gentle.nexus.transaction.controller;

import com.gentle.nexus.common.utils.SecurityUtil;
import com.gentle.nexus.transaction.dto.DepositRequestDto;
import com.gentle.nexus.transaction.dto.TransferRequestDto;
import com.gentle.nexus.transaction.dto.WithdrawRequestDto;
import com.gentle.nexus.transaction.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/deposit")
    public ResponseEntity<String> deposit(
            Authentication authentication,
            @RequestBody DepositRequestDto dto
    ) {
        Long userId = SecurityUtil.getCurrentUserId(authentication);
        transactionService.deposit(userId, dto);
        return ResponseEntity.ok("입금 성공");
    }

    @PostMapping("/withdraw")
    public ResponseEntity<String> withdraw(
            Authentication authentication,
            @RequestBody WithdrawRequestDto dto
    ) {
        Long userId = SecurityUtil.getCurrentUserId(authentication);
        transactionService.withdraw(userId, dto);
        return ResponseEntity.ok("출금 성공");
    }

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(
            Authentication authentication,
            @RequestBody TransferRequestDto dto
    ) {
        Long userId = SecurityUtil.getCurrentUserId(authentication);
        transactionService.transfer(userId, dto);
        return ResponseEntity.ok("송금 성공");
    }

}
