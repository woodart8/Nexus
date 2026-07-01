package com.gentle.nexus.transaction.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class WithdrawRequestDto {

    @NotNull
    private Long accountId;

    @NotNull
    @Positive
    private BigDecimal amount;

}
