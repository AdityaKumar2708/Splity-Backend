package com.rkind.splity.wallet.entity;

import com.rkind.splity.entity.User;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallets")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(nullable = false, unique = true)
    private String walletNumber;

    @Column(nullable = false, precision = 18, scale = 2)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private WalletStatus status = WalletStatus.NOT_ACTIVATED;

    // ---------------- Security ----------------

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @Column(nullable = false)
    private String walletPinHash;

    @Column(nullable = false)
    private String trustedDeviceId;

    @Column(nullable = false)
    private Boolean walletVerified = false;

    // ---------------- Audit ----------------

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    private Integer failedPinAttempts = 0;

    private LocalDateTime walletLockedUntil;

    @PrePersist
    public void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ================= GETTERS =================

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public String getWalletNumber() {
        return walletNumber;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public WalletStatus getStatus() {
        return status;
    }

    public String getEmail() {
        return email;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public String getWalletPinHash() {
        return walletPinHash;
    }

    public String getTrustedDeviceId() {
        return trustedDeviceId;
    }

    public Boolean getWalletVerified() {
        return walletVerified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    // ================= SETTERS =================

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public void setStatus(WalletStatus status) {
        this.status = status;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public void setWalletPinHash(String walletPinHash) {
        this.walletPinHash = walletPinHash;
    }

    public void setTrustedDeviceId(String trustedDeviceId) {
        this.trustedDeviceId = trustedDeviceId;
    }

    public void setWalletVerified(Boolean walletVerified) {
        this.walletVerified = walletVerified;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Integer getFailedPinAttempts() {
        return failedPinAttempts;
    }

    public void setFailedPinAttempts(Integer failedPinAttempts) {
        this.failedPinAttempts = failedPinAttempts;
    }

    public LocalDateTime getWalletLockedUntil() {
        return walletLockedUntil;
    }

    public void setWalletLockedUntil(LocalDateTime walletLockedUntil) {
        this.walletLockedUntil = walletLockedUntil;
    }
}