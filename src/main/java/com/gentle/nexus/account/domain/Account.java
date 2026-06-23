package com.gentle.nexus.account.domain;

import com.gentle.nexus.common.entity.BaseTimeEntity;
import com.gentle.nexus.common.exception.BusinessException;
import com.gentle.nexus.common.exception.ErrorCode;
import com.gentle.nexus.user.domain.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;

@Entity
@Table(name = "account")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class Account extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 계좌 소유자
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // 계좌번호
    @Column(nullable = false, unique = true)
    private String accountNumber;

    // 계좌 비밀번호 (평문 저장 X)
    @Column(nullable = false)
    private String password;

    // 현재 잔액
    @Column(nullable = false)
    private BigDecimal balance;

    // 계좌 상태
    @Enumerated(EnumType.STRING)
    private AccountStatus status;

    // 계좌 종류
    @Enumerated(EnumType.STRING)
    private AccountType type;

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeStatus(AccountStatus status) {
        this.status = status;
    }

    public void deposit(BigDecimal amount) {
        validateAmount(amount);
        this.balance = this.balance.add(amount);
    }

    public void withdraw(BigDecimal amount) {
        validateAmount(amount);

        if (this.balance.compareTo(amount) < 0) {
            throw new BusinessException(ErrorCode.NOT_ENOUGH_BALANCE);
        }

        this.balance = this.balance.subtract(amount);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BusinessException(ErrorCode.AMOUNT_MUST_BE_POSITIVE);
        }
    }

}
