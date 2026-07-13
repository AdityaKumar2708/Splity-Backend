package com.rkind.splity.repository;

import com.rkind.splity.entity.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GroupMessageRepository extends JpaRepository<GroupMessage, Long> {

    List<GroupMessage> findByGroupIdOrderByCreatedAtAsc(Long groupId);

    Optional<GroupMessage> findByIdAndGroupId(Long id, Long groupId);

    List<GroupMessage> findByGroupIdAndMessageTypeOrderByCreatedAtDesc(
            Long groupId,
            String messageType
    );

    List<GroupMessage> findByGroupIdAndMessageTypeInOrderByCreatedAtDesc(
            Long groupId,
            List<String> messageTypes
    );

    List<GroupMessage> findByGroupIdAndCreatedAtGreaterThanEqualOrderByCreatedAtAsc(
            Long groupId,
            LocalDateTime joinedAt
    );
}