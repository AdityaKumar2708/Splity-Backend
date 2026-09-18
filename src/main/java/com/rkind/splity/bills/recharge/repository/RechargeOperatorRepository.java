package com.rkind.splity.bills.recharge.repository;

import com.rkind.splity.bills.recharge.entity.RechargeOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RechargeOperatorRepository extends JpaRepository<RechargeOperator, Long> {

    Optional<RechargeOperator> findByName(String name);

    Optional<RechargeOperator> findByProviderCode(String providerCode);

    List<RechargeOperator> findByActiveTrueOrderByDisplayOrderAsc();

    boolean existsByName(String name);

    boolean existsByProviderCode(String providerCode);

}
