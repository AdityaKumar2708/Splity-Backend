package com.rkind.splity.expense.repository;

import com.rkind.splity.expense.entity.ExpenseTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository
        extends JpaRepository<ExpenseTransaction, Long> {

    List<ExpenseTransaction> findByUserIdOrderByCreatedAtDesc(
            Long userId
    );

    List<ExpenseTransaction> findByGroupIdOrderByCreatedAtDesc(
            Long groupId
    );

    List<ExpenseTransaction> findByGroupIdAndSplitDoneFalse(
            Long groupId
    );

    Optional<ExpenseTransaction> findByTransactionId(
            String transactionId
    );

}