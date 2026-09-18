package com.rkind.splity.wallet.repository;

import com.rkind.splity.wallet.entity.Wallet;
import com.rkind.splity.wallet.entity.WalletTransaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WalletTransactionRepository extends JpaRepository<WalletTransaction, Long> {

    List<WalletTransaction> findByWalletOrderByCreatedAtDesc(Wallet wallet);

    Optional<WalletTransaction> findByRazorpayOrderId(String razorpayOrderId);

    Optional<WalletTransaction> findByRazorpayPaymentId(String razorpayPaymentId);

    boolean existsByRazorpayPaymentId(String razorpayPaymentId);
}