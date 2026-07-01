package com.gentle.nexus.account.repository;

import com.gentle.nexus.account.domain.Account;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByAccountNumber(String accountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findWithLockByAccountNumber(String accountNumber);

    Optional<Account> findByIdAndUserId(Long fromAccountId, Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findWithLockByIdAndUserId(Long accountId, Long userId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Account> findWithLockById(Long id);

}
