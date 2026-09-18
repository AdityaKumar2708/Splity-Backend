package com.rkind.splity.splityai.repository;

import com.rkind.splity.splityai.entity.AIConversation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AIConversationRepository extends JpaRepository<AIConversation, Long> {

    Optional<AIConversation> findByConversationId(String conversationId);

}