package com.gentle.nexus.transaction.repository;

import com.gentle.nexus.transaction.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
