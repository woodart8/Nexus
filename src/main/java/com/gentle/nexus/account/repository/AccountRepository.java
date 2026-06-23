package com.gentle.nexus.account.repository;

import com.gentle.nexus.account.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
