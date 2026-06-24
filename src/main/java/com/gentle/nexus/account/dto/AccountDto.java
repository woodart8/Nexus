package com.gentle.nexus.account.dto;

import com.gentle.nexus.account.domain.Account;
import com.gentle.nexus.account.domain.AccountStatus;
import com.gentle.nexus.account.domain.AccountType;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AccountDto {

    private Long id;
    private String accountNumber;
    private BigDecimal balance;
    private AccountStatus status;
    private AccountType type;
    private Long userId;

    public static AccountDto from(Account account) {
        return AccountDto.builder()
                .id(account.getId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .status(account.getStatus())
                .type(account.getType())
                .userId(account.getUser().getId())
                .build();
    }

}
