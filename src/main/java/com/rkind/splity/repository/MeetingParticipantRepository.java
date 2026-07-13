package com.rkind.splity.repository;

import com.rkind.splity.entity.MeetingParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MeetingParticipantRepository
        extends JpaRepository<MeetingParticipant, Long> {

    // All participants
    List<MeetingParticipant> findByMeetingId(Long meetingId);

    // Active participants
    List<MeetingParticipant> findByMeetingIdAndLeftAtIsNull(Long meetingId);

    // Single participant
    Optional<MeetingParticipant> findByMeetingIdAndUserId(
            Long meetingId,
            Long userId
    );

    // Active participant count
    long countByMeetingIdAndLeftAtIsNull(Long meetingId);

    // Has user joined?
    boolean existsByMeetingIdAndUserId(
            Long meetingId,
            Long userId
    );

    // Active participant?
    boolean existsByMeetingIdAndUserIdAndLeftAtIsNull(
            Long meetingId,
            Long userId
    );

    // Participant count
    long countByMeetingId(Long meetingId);

    // User meeting history
    List<MeetingParticipant> findAllByUserIdOrderByJoinedAtDesc(
            Long userId
    );

    List<MeetingParticipant>
    findByMeetingIdOrderByJoinedAtAsc(
            Long meetingId
    );
}