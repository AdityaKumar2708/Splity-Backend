package com.rkind.splity.support.wallet.repository;

import com.rkind.splity.support.wallet.entity.SupportTicket;
import com.rkind.splity.support.wallet.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long> {

    Optional<SupportTicket> findByTicketNumber(String ticketNumber);

    List<SupportTicket> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<SupportTicket> findByStatus(TicketStatus status);
    long countByCreatedAtBetween(
            java.time.LocalDateTime start,
            java.time.LocalDateTime end
    );

    boolean existsByTicketNumber(String ticketNumber);

}