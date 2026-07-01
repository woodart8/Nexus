package com.gentle.nexus.transaction.repository;

import com.gentle.nexus.transaction.domain.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionHistoryRepository extends JpaRepository<TransactionHistory, Long> {
}
