package com.rkind.splity.bills.recharge.entity;

import jakarta.persistence.*;
import lombok.*;
import org.checkerframework.checker.units.qual.C;

import java.time.LocalDateTime;

@Entity
@Table(name = "recharge_operator")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeOperator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String displayName;

    @Column(nullable = false, unique = false)
    private String providerCode;

    private String logoUrl;

    private String website;

    private String customerCareNumber;

    @Builder.Default
    private boolean active = true;

    private Integer displayOrder;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();



}
