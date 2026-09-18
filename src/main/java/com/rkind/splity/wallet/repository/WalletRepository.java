package com.rkind.splity.wallet.repository;

import com.rkind.splity.entity.User;
import com.rkind.splity.wallet.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {

    Optional<Wallet> findByUser(User user);

    Optional<Wallet> findByUserId(Long userId);

    Optional<Wallet> findByWalletNumber(String walletNumber);

    boolean existsByWalletNumber(String walletNumber);
    boolean existsByUserId(Long userId);

}