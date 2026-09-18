package com.rkind.splity.bills.recharge.repository;

import com.rkind.splity.bills.recharge.entity.RechargeHistory;
import com.rkind.splity.bills.recharge.entity.RechargeOperator;
import com.rkind.splity.bills.recharge.enums.RechargeStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RechargeHistoryRepository extends JpaRepository<RechargeHistory, Long> {

    List<RechargeHistory> findByUserId(Long userId);

    Optional<RechargeHistory> findByTransactionId(String transactionId);

    List<RechargeHistory> findByStatus(RechargeStatus status);

    List<RechargeHistory> findByOperatorId(Long operatorId);

    List<RechargeHistory> findByAmountGreaterThan(BigDecimal amount);

    List<RechargeHistory> findByMobileNumber(String mobileNumber);

    List<RechargeHistory> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<RechargeHistory>
    findByUserIdAndStatus(
            Long userId,
            RechargeStatus status
    );

    boolean existsByTransactionId(String transactionId);

}
