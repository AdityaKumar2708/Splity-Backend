package com.rkind.splity.bills.recharge.repository;

import com.rkind.splity.bills.recharge.entity.RechargeCircle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface RechargeCircleRepository extends JpaRepository<RechargeCircle, Long> {

    Optional<RechargeCircle> findByName(String name);

    Optional<RechargeCircle> findByProviderCode(String providerCode);

    List<RechargeCircle> findByActiveTrue();

    List<RechargeCircle> findByActiveTrueOrderByDisplayOrderAsc();

    boolean existsByName(String name);
    boolean existsByProviderCode(String providerCode);
}
