package com.gentle.nexus.transaction.service;

import com.gentle.nexus.account.domain.Account;
import com.gentle.nexus.account.repository.AccountRepository;
import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
import com.gentle.nexus.transaction.domain.Transaction;
import com.gentle.nexus.transaction.domain.TransactionHistory;
import com.gentle.nexus.transaction.domain.TransactionType;
import com.gentle.nexus.transaction.dto.DepositRequestDto;
import com.gentle.nexus.transaction.dto.TransferRequestDto;
import com.gentle.nexus.transaction.dto.WithdrawRequestDto;
import com.gentle.nexus.transaction.factory.TransactionFactory;
import com.gentle.nexus.transaction.repository.TransactionHistoryRepository;
import com.gentle.nexus.transaction.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionHistoryRepository transactionHistoryRepository;

    @Transactional
    public void deposit(Long userId, DepositRequestDto depositRequestDto) {
        Account account =
                accountRepository
                        .findWithLockByIdAndUserId(
                                depositRequestDto.getAccountId(),
                                userId
                        )
                        .orElseThrow(
                                () -> new BusinessException(ErrorCode.FORBIDDEN)
                        );

        account.deposit(depositRequestDto.getAmount());

        Transaction transaction = TransactionFactory.deposit(
                account,
                depositRequestDto.getAmount()
        );

        transactionRepository.save(transaction);

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .transaction(transaction)
                .account(account)
                .changeAmount(depositRequestDto.getAmount())
                .balanceAfter(account.getBalance())
                .build();

        transactionHistoryRepository.save(transactionHistory);
    }

    @Transactional
    public void withdraw(Long userId, WithdrawRequestDto withdrawRequestDto) {
        Account account =
                accountRepository
                        .findWithLockByIdAndUserId(
                                withdrawRequestDto.getAccountId(),
                                userId
                        )
                        .orElseThrow(
                                () -> new BusinessException(ErrorCode.FORBIDDEN)
                        );

        account.withdraw(withdrawRequestDto.getAmount());

        Transaction transaction = TransactionFactory.withdraw(
                account,
                withdrawRequestDto.getAmount()
        );

        transactionRepository.save(transaction);

        TransactionHistory transactionHistory = TransactionHistory.builder()
                .transaction(transaction)
                .account(account)
                .changeAmount(withdrawRequestDto.getAmount().negate())
                .balanceAfter(account.getBalance())
                .build();

        transactionHistoryRepository.save(transactionHistory);
    }

    @Transactional
    public void transfer(Long userId, TransferRequestDto transferRequestDto) {

        Account fromAccount =
                accountRepository
                        .findByIdAndUserId(
                                transferRequestDto.getFromAccountId(),
                                userId
                        )
                        .orElseThrow(
                                () -> new BusinessException(ErrorCode.FORBIDDEN)
                        );


        Account toAccount =
                accountRepository
                        .findByAccountNumber(
                                transferRequestDto.getToAccountNumber()
                        )
                        .orElseThrow(
                                () -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND)
                        );

        if (fromAccount.getAccountNumber()
                .equals(toAccount.getAccountNumber())) {
            throw new BusinessException(ErrorCode.INVALID_TRANSFER);
        }

        Long firstId = Math.min(
                fromAccount.getId(),
                toAccount.getId()
        );

        Long secondId = Math.max(
                fromAccount.getId(),
                toAccount.getId()
        );


        Account firstAccount =
                accountRepository
                        .findWithLockById(firstId)
                        .orElseThrow(
                                () -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND)
                        );


        Account secondAccount =
                accountRepository
                        .findWithLockById(secondId)
                        .orElseThrow(
                                () -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND)
                        );


        Account sender =
                fromAccount.getId().equals(firstAccount.getId())
                        ? firstAccount
                        : secondAccount;


        Account receiver =
                toAccount.getId().equals(firstAccount.getId())
                        ? firstAccount
                        : secondAccount;


        sender.withdraw(transferRequestDto.getAmount());

        receiver.deposit(transferRequestDto.getAmount());


        Transaction transaction = TransactionFactory.transfer(
                sender,
                receiver,
                transferRequestDto.getAmount()
        );


        transactionRepository.save(transaction);


        TransactionHistory senderHistory =
                TransactionHistory.builder()
                        .transaction(transaction)
                        .account(sender)
                        .changeAmount(
                                transferRequestDto.getAmount().negate()
                        )
                        .balanceAfter(sender.getBalance())
                        .build();


        TransactionHistory receiverHistory =
                TransactionHistory.builder()
                        .transaction(transaction)
                        .account(receiver)
                        .changeAmount(
                                transferRequestDto.getAmount()
                        )
                        .balanceAfter(receiver.getBalance())
                        .build();


        transactionHistoryRepository.save(senderHistory);
        transactionHistoryRepository.save(receiverHistory);
    }

}
