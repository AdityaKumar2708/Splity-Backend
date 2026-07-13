package com.rkind.splity.repository;

import com.rkind.splity.entity.Meeting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    // Current active meeting of a group
    Optional<Meeting> findByGroupIdAndStatus(
            Long groupId,
            String status
    );

    // All meetings of a group
    List<Meeting> findAllByGroupIdOrderByStartedAtDesc(
            Long groupId
    );

    // Meeting history of a host
    List<Meeting> findAllByStartedByOrderByStartedAtDesc(
            Long startedBy
    );

    // Check if a meeting is active
    boolean existsByIdAndStatus(
            Long meetingId,
            String status
    );

    Optional<Meeting> findByIdAndStatus(
            Long id,
            String status
    );
}