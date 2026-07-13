package com.rkind.splity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rkind.splity.entity.GroupJoinRequest;

public interface GroupJoinRequestRepository extends JpaRepository<GroupJoinRequest, Long> {

    Optional<GroupJoinRequest> findByGroupIdAndUserId(
            Long groupId,
            Long userId
    );

    Optional<GroupJoinRequest> findByGroupIdAndUserIdAndStatus(
            Long groupId,
            Long userId,
            String status
    );

    List<GroupJoinRequest> findByGroupIdAndStatus(
            Long groupId,
            String status
    );

    List<GroupJoinRequest> findByUserIdAndStatus(
            Long userId,
            String status
    );

    List<GroupJoinRequest> findByUserIdOrderByRequestedAtDesc(
            Long userId
    );

    void deleteByGroupIdAndUserId(
            Long groupId,
            Long userId
    );

}