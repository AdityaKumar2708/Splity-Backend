package com.rkind.splity.wallet.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet_ledger")
public class WalletLedger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private Wallet wallet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id")
    private WalletTransaction transaction;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal openingBalance;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal closingBalance;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public WalletTransaction getTransaction() {
        return transaction;
    }

    public BigDecimal getOpeningBalance() {
        return openingBalance;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public BigDecimal getClosingBalance() {
        return closingBalance;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public void setTransaction(WalletTransaction transaction) {
        this.transaction = transaction;
    }

    public void setOpeningBalance(BigDecimal openingBalance) {
        this.openingBalance = openingBalance;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public void setClosingBalance(BigDecimal closingBalance) {
        this.closingBalance = closingBalance;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}