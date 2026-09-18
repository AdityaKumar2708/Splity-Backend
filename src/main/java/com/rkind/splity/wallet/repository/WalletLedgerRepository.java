package com.rkind.splity.wallet.repository;

import com.rkind.splity.wallet.entity.WalletLedger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletLedgerRepository
        extends JpaRepository<WalletLedger, Long> {

}