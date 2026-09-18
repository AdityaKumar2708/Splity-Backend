package com.rkind.splity.repository;

import com.rkind.splity.entity.GroupMember;
import com.rkind.splity.entity.GroupMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository
        extends JpaRepository<GroupMember, Long> {

    boolean existsByUserId(Long userId);
    boolean existsByUserIdAndGroupId(Long userId, Long groupId);

    List<GroupMember> findAllByUserId(Long userId);

    List<GroupMember> findAllByGroupId(Long groupId);

    Optional<GroupMember> findByUserIdAndGroupId(
            Long userId,
            Long groupId
    );

    long countByGroupId(Long groupId);
}
