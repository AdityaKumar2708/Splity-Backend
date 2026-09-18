package com.rkind.splity.bills.recharge.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "recharge_circle")
@Getter
@Setter
@Builder
@NoArgsConstructor@AllArgsConstructor
public class RechargeCircle {

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String name;

    @Column(nullable = false, length = 100)
    private String displayName;


    @Column(nullable = false, unique = true)
    private String providerCode;


    @Builder.Default
    private Boolean active = true;

    private Integer displayOrder;

    @Builder.Default
    private LocalDateTime createdAt =
            LocalDateTime.now();

    @Builder.Default
    private LocalDateTime updatedAt =
            LocalDateTime.now();
}
