package com.rkind.splity.bills.recharge.entity;


import com.rkind.splity.bills.recharge.enums.RechargeStatus;
import com.rkind.splity.bills.recharge.enums.RechargeType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recharge_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 15)
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RechargeType rechargeType;

    @Column(nullable = false)
    private Long operatorId;

    @Column(nullable = false)
    private Long circleId;

    private String message;
    private Long planId;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, unique = true)
    private String transactionId;

    private Long providerTransactionId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RechargeStatus status;


    private String paymentMode;

    @Column(length = 500)
    private String remarks;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}
