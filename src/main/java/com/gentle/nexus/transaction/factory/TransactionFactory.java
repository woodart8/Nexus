package com.gentle.nexus.transaction.factory;

import com.gentle.nexus.account.domain.Account;
import com.gentle.nexus.transaction.domain.Transaction;
import com.gentle.nexus.transaction.domain.TransactionType;

import java.math.BigDecimal;

public class TransactionFactory {

    public static Transaction deposit(Account to, BigDecimal amount) {
        return Transaction.builder()
                .type(TransactionType.DEPOSIT)
                .toAccount(to)
                .amount(amount)
                .build();
    }

    public static Transaction withdraw(Account from, BigDecimal amount) {
        return Transaction.builder()
                .type(TransactionType.WITHDRAW)
                .fromAccount(from)
                .amount(amount)
                .build();
    }

    public static Transaction transfer(Account from, Account to, BigDecimal amount) {
        return Transaction.builder()
                .type(TransactionType.TRANSFER)
                .fromAccount(from)
                .toAccount(to)
                .amount(amount)
                .build();
    }
}
