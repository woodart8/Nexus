package com.gentle.nexus.account.repository;

import com.gentle.nexus.account.domain.AccountNumberSeq;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountNumberSeqRepository extends JpaRepository<AccountNumberSeq, Long> {
}
