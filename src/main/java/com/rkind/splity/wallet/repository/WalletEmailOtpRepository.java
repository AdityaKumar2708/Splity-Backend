package com.rkind.splity.wallet.repository;

import com.rkind.splity.wallet.entity.WalletEmailOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletEmailOtpRepository
        extends JpaRepository<WalletEmailOtp, Long> {

    Optional<WalletEmailOtp> findTopByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<WalletEmailOtp> findTopByEmailOrderByCreatedAtDesc(String email);

    Optional<WalletEmailOtp> findByUserIdAndEmailAndOtp(
            Long userId,
            String email,
            String otp
    );

}