package com.rkind.splity.bills.recharge.repository;

import com.rkind.splity.bills.recharge.entity.RechargeCircle;
import com.rkind.splity.bills.recharge.entity.RechargeOperator;
import com.rkind.splity.bills.recharge.entity.RechargePlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface RechargePlanRepository extends JpaRepository<RechargePlan, Long> {

    Optional<RechargePlan> findByProviderPlanCode(String providerPlanCode);

    boolean existsByProviderPlanCode(String providerPlanCode);

    List<RechargePlan> findByActiveTrue();

    List<RechargePlan> findByOperator(RechargeOperator operator);

    List<RechargePlan> findByCircle(RechargeCircle circle);

    List<RechargePlan> findByOperatorAndCircle(RechargeOperator operator, RechargeCircle circle);

    List<RechargePlan> findByOperatorAndCircleAndActiveTrue(RechargeOperator operator, RechargeCircle circle);

    List<RechargePlan> findByActiveTrueOrderByAmountAsc();

    List<RechargePlan> findByAmountGreaterThan(BigDecimal amount);


}
