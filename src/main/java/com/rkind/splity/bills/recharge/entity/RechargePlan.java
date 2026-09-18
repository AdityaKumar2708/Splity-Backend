package com.rkind.splity.bills.recharge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "recharge_plans")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargePlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private RechargeOperator operator;

    @ManyToOne
    @JoinColumn(name = "circle_id")
    private RechargeCircle circle;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false)
    private String validity;
    private String planName;
    private String dataBenefit;
    private String voiceBenefit;
    private String smsBenefit;
    private String ottBenefit;

    @Lob
    private String description;

    @Column(nullable = false, unique = true)
    private String providerPlanCode;

    @Builder.Default
    private boolean active = true;

    private Integer displayOrder;

    @Builder.Default
    private LocalDateTime createdAt =
            LocalDateTime.now();


    @Builder.Default
    private LocalDateTime updatedAt =
            LocalDateTime.now();

    

}
