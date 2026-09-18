package com.rkind.splity.splityai.repository;

import com.rkind.splity.splityai.entity.AIConversationMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AIConversationMessageRepository extends JpaRepository<AIConversationMessage, Long> {

    List<AIConversationMessage> findByConversationIdOrderByCreatedAtAsc(String conversationId);

}