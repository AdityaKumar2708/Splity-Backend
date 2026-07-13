package com.rkind.splity.repository;

import com.rkind.splity.entity.GroupChatClear;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupChatClearRepository
        extends JpaRepository<GroupChatClear, Long> {

    Optional<GroupChatClear> findByGroupIdAndUserId(
            Long groupId,
            Long userId
    );
}