package com.rkind.splity.splityai.repository;

import com.rkind.splity.splityai.entity.AIConversationSummary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AIConversationSummaryRepository extends JpaRepository<AIConversationSummary, Long> {

    Optional<AIConversationSummary> findByConversationId(String conversationId);

}