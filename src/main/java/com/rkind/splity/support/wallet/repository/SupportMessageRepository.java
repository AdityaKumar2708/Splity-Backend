package com.rkind.splity.support.wallet.repository;

import com.rkind.splity.support.wallet.entity.SupportMessage;
import com.rkind.splity.support.wallet.entity.SupportTicket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SupportMessageRepository extends JpaRepository<SupportMessage, Long> {

    List<SupportMessage> findByTicketOrderByCreatedAtAsc(SupportTicket ticket);
    List<SupportMessage> findByTicket_TicketNumberOrderByCreatedAtAsc(String ticketNumber);

}