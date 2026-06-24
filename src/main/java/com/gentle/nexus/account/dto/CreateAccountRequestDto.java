package com.gentle.nexus.account.dto;

import com.gentle.nexus.account.domain.AccountType;
import lombok.Getter;

@Getter
public class CreateAccountRequestDto {
    private String password;
    private AccountType accountType;
}
