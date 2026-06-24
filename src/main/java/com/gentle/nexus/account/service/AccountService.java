package com.gentle.nexus.account.service;

import com.gentle.nexus.account.domain.Account;
import com.gentle.nexus.account.domain.AccountNumberSeq;
import com.gentle.nexus.account.domain.AccountStatus;
import com.gentle.nexus.account.dto.AccountDto;
import com.gentle.nexus.account.dto.ChangeAccountPasswordDto;
import com.gentle.nexus.account.dto.CreateAccountRequestDto;
import com.gentle.nexus.account.repository.AccountNumberSeqRepository;
import com.gentle.nexus.account.repository.AccountRepository;
import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
import com.gentle.nexus.user.domain.User;
import com.gentle.nexus.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final UserRepository userRepository;
    private final AccountRepository accountRepository;
    private final AccountNumberSeqRepository accountNumberSeqRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public AccountDto createAccount(Long userId, CreateAccountRequestDto req) {
        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

            AccountNumberSeq seq = AccountNumberSeq.builder().build();
            accountNumberSeqRepository.save(seq);

            String accountNumber = generateAccountNumber(seq.getId());

            Account account = Account.builder()
                    .accountNumber(accountNumber)
                    .balance(BigDecimal.ZERO)
                    .password(passwordEncoder.encode(req.getPassword()))
                    .status(AccountStatus.ACTIVE)
                    .type(req.getAccountType())
                    .user(user)
                    .build();

            accountRepository.save(account);

            return AccountDto.from(account);
        } catch (
        DataIntegrityViolationException e) {
            throw new BusinessException(ErrorCode.DATA_INTEGRITY_VIOLATION);
        } catch (
        DataAccessException e) {
            throw new BusinessException(ErrorCode.DB_ERROR);
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public void modifyAccountPassword(Long userId, ChangeAccountPasswordDto req) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        Account account = accountRepository.findByAccountNumber(req.getAccountNumber())
                .orElseThrow(() -> new BusinessException(ErrorCode.ACCOUNT_NOT_FOUND));

        if (!account.getUser().getId().equals(user.getId())) {
            throw new BusinessException(ErrorCode.FORBIDDEN);
        }

        account.changePassword(passwordEncoder.encode(req.getPassword()));
    }

    private String generateAccountNumber(Long accountId) {
        String bankCode = "110";   // 은행 코드
        String branchCode = "001"; // 지점 코드

        return bankCode
                + branchCode
                + String.format("%08d", accountId);
    }

}
