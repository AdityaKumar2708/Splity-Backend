package com.rkind.splity.support.wallet.repository;

import com.rkind.splity.support.wallet.entity.ChatConversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatConversationRepository extends JpaRepository<ChatConversation, Long> {

    List<ChatConversation> findTop10ByUserIdOrderByCreatedAtDesc(Long userId);

    List<ChatConversation> findTop20ByTicketNumberOrderByCreatedAtDesc(String ticketNumber);

}