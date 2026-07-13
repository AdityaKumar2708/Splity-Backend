package com.rkind.splity.service;

import com.rkind.splity.dto.SocketMessageDto;
import com.rkind.splity.dto.meeting.*;
import com.rkind.splity.entity.GroupMember;
import com.rkind.splity.entity.Meeting;
import com.rkind.splity.entity.MeetingParticipant;
import com.rkind.splity.repository.GroupMemberRepository;
import com.rkind.splity.repository.MeetingParticipantRepository;
import com.rkind.splity.repository.MeetingRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final MeetingParticipantRepository participantRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public MeetingService(
            MeetingRepository meetingRepository,
            MeetingParticipantRepository participantRepository,
            GroupMemberRepository groupMemberRepository,
            SimpMessagingTemplate messagingTemplate
    ) {
        this.meetingRepository = meetingRepository;
        this.participantRepository = participantRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Transactional
    public MeetingResponseDto startMeeting(StartMeetingRequestDto request) {
        System.out.println("========== START MEETING ==========");

        Optional<Meeting> existingMeeting =
                meetingRepository.findByGroupIdAndStatus(
                        request.getGroupId(),
                        "ACTIVE"
                );

        if (existingMeeting.isPresent()) {
            System.out.println("RETURNING OLD MEETING");
            Meeting meeting = existingMeeting.get();

            MeetingResponseDto response = new MeetingResponseDto();

            // FIX: Fetch only active participants (instead of all group members)
            List<Long> participantIds =
                    participantRepository.findByMeetingId(meeting.getId())
                            .stream()
                            .filter(p -> p.getLeftAt() == null)
                            .map(MeetingParticipant::getUserId)
                            .toList();

            response.setMeetingId(meeting.getId());
            response.setGroupId(meeting.getGroupId());
            response.setHostUserId(meeting.getStartedBy());
            response.setRoomId("ROOM_" + meeting.getId());
            response.setActive(true);
            response.setParticipantIds(participantIds);

            return response;
        }

        System.out.println("CREATING NEW MEETING");
        Meeting meeting = new Meeting();
        meeting.setGroupId(request.getGroupId());
        meeting.setStartedBy(request.getHostUserId());
        meeting.setMeetingType("VOICE");
        meeting.setStatus("ACTIVE");
        meeting.setStartedAt(LocalDateTime.now());
        meeting = meetingRepository.save(meeting);

        MeetingParticipant participant = new MeetingParticipant();
        participant.setMeetingId(meeting.getId());
        participant.setUserId(request.getHostUserId());
        participant.setJoinedAt(LocalDateTime.now());
        participantRepository.save(participant);

        List<GroupMember> members =
                groupMemberRepository.findAllByGroupId(meeting.getGroupId());

        if (members == null) {
            throw new RuntimeException("Members list is NULL");
        }

        // Host is the only participant in the list initially
        List<Long> participantIds = List.of(request.getHostUserId());

        for (GroupMember member : members) {
            if (member.getUserId().equals(request.getHostUserId())) {
                continue;
            }

            SocketMessageDto socket = new SocketMessageDto();
            socket.setType("INCOMING_CALL");
            socket.setMeetingId(meeting.getId());
            socket.setGroupId(meeting.getGroupId());
            socket.setHostUserId(meeting.getStartedBy());
            socket.setMeetingType("VOICE");
            socket.setReceiverId(member.getUserId());
            socket.setParticipantIds(participantIds);

            messagingTemplate.convertAndSend(
                    "/topic/meeting/" + member.getUserId(),
                    socket
            );
        }

        MeetingResponseDto response = new MeetingResponseDto();
        response.setMeetingId(meeting.getId());
        response.setGroupId(meeting.getGroupId());
        response.setHostUserId(meeting.getStartedBy());
        response.setRoomId("ROOM_" + meeting.getId());
        response.setActive(true);
        response.setParticipantIds(participantIds);

        return response;
    }

    @Transactional
    public void joinMeeting(JoinMeetingRequestDto request) {
        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        if (!"ACTIVE".equals(meeting.getStatus())) {
            throw new RuntimeException("Meeting has already ended");
        }

        Optional<MeetingParticipant> existing =
                participantRepository.findByMeetingIdAndUserId(
                        request.getMeetingId(),
                        request.getUserId()
                );

        boolean isNewJoin = true;

        if (existing.isPresent()) {
            MeetingParticipant participant = existing.get();
            if (participant.getLeftAt() == null) {
                isNewJoin = false; // Already in the call
            }
            participant.setJoinedAt(LocalDateTime.now());
            participant.setLeftAt(null);
            participantRepository.save(participant);
        } else {
            MeetingParticipant participant = new MeetingParticipant();
            participant.setMeetingId(request.getMeetingId());
            participant.setUserId(request.getUserId());
            participant.setJoinedAt(LocalDateTime.now());
            participant.setMuted(false);
            participant.setSpeaker(false);
            participantRepository.save(participant);
        }

        // Notify other participants that a user has joined
        if (isNewJoin) {
            notifyParticipantsOfChange(meeting, "USER_JOINED", request.getUserId());
        }
    }

    @Transactional
    public void leaveMeeting(JoinMeetingRequestDto request) {
        MeetingParticipant participant =
                participantRepository
                        .findByMeetingIdAndUserId(
                                request.getMeetingId(),
                                request.getUserId()
                        )
                        .orElseThrow(() -> new RuntimeException("Participant not found"));

        if (participant.getLeftAt() != null) {
            return;
        }

        participant.setLeftAt(LocalDateTime.now());
        participantRepository.save(participant);

        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        // Notify remaining participants that a user left
        notifyParticipantsOfChange(meeting, "USER_LEFT", request.getUserId());

        // Perform cleanup checks (auto-close meeting if needed)
        updateMeetingStatusAndCleanup(meeting);
    }

    @Transactional
    public void rejectMeeting(RejectMeetingRequestDto request) {
        MeetingParticipant participant =
                participantRepository
                        .findByMeetingIdAndUserId(
                                request.getMeetingId(),
                                request.getUserId()
                        )
                        .orElse(null);

        if (participant == null) {
            participant = new MeetingParticipant();
            participant.setMeetingId(request.getMeetingId());
            participant.setUserId(request.getUserId());
            // FIX: Removed setting joinedAt here since the user rejected
        }

        participant.setLeftAt(LocalDateTime.now());
        participantRepository.save(participant);

        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        // Notify the host about the rejection
        SocketMessageDto dto = new SocketMessageDto();
        dto.setType("CALL_REJECTED");
        dto.setMeetingId(meeting.getId());
        dto.setGroupId(meeting.getGroupId());
        dto.setHostUserId(meeting.getStartedBy());
        dto.setSenderId(request.getUserId());

        messagingTemplate.convertAndSend(
                "/topic/meeting/" + meeting.getStartedBy(),
                dto
        );

        // Perform cleanup checks (auto-close meeting if needed)
        updateMeetingStatusAndCleanup(meeting);
    }

    @Transactional
    public void endMeeting(EndMeetingRequestDto request) {
        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        if (!meeting.getStartedBy().equals(request.getHostUserId())) {
            throw new RuntimeException("Only host can end the meeting");
        }

        if ("ENDED".equals(meeting.getStatus())) {
            return;
        }

        closeMeeting(meeting);
    }

    @Transactional
    public void updateMuteStatus(MuteStatusRequestDto request) {
        MeetingParticipant participant = participantRepository
                .findByMeetingIdAndUserId(request.getMeetingId(), request.getUserId())
                .orElseThrow(() -> new RuntimeException("Participant not found"));

        participant.setMuted(request.isMuted());
        participantRepository.save(participant);

        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        notifyParticipantsOfMediaChange(meeting, "MUTE_CHANGED", request.getUserId(), request.isMuted(), null);
    }

    @Transactional
    public void updateSpeakerStatus(SpeakerStatusRequestDto request) {
        MeetingParticipant participant = participantRepository
                .findByMeetingIdAndUserId(request.getMeetingId(), request.getUserId())
                .orElseThrow(() -> new RuntimeException("Participant not found"));

        participant.setSpeaker(request.isSpeaker());
        participantRepository.save(participant);

        Meeting meeting = meetingRepository.findById(request.getMeetingId())
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        notifyParticipantsOfMediaChange(meeting, "SPEAKER_CHANGED", request.getUserId(), null, request.isSpeaker());
    }

    public MeetingResponseDto getMeeting(Long meetingId) {
        Meeting meeting = meetingRepository.findById(meetingId)
                .orElseThrow(() -> new RuntimeException("Meeting not found"));

        MeetingResponseDto dto = new MeetingResponseDto();
        dto.setMeetingId(meeting.getId());
        dto.setGroupId(meeting.getGroupId());
        dto.setHostUserId(meeting.getStartedBy());
        dto.setRoomId("ROOM_" + meeting.getId());
        dto.setActive("ACTIVE".equals(meeting.getStatus()));

        List<Long> participantIds =
                participantRepository
                        .findByMeetingId(meetingId)
                        .stream()
                        .filter(p -> p.getLeftAt() == null)
                        .map(MeetingParticipant::getUserId)
                        .toList();

        dto.setParticipantIds(participantIds);
        return dto;
    }

    // Helper: Closes a meeting and marks remaining active participants as left
    private void closeMeeting(Meeting meeting) {
        meeting.setStatus("ENDED");
        meeting.setEndedAt(LocalDateTime.now());
        meetingRepository.save(meeting);

        List<MeetingParticipant> participants =
                participantRepository.findByMeetingId(meeting.getId());

        for (MeetingParticipant participant : participants) {
            if (participant.getLeftAt() == null) {
                participant.setLeftAt(LocalDateTime.now());
                participantRepository.save(participant);
            }
        }

        // Notify all group members that the call has ended
        notifyGroupMembersOfEnd(meeting);
    }

    // Helper: Evaluates whether to auto-close the meeting
    private void updateMeetingStatusAndCleanup(Meeting meeting) {
        if (!"ACTIVE".equals(meeting.getStatus())) {
            return;
        }

        List<MeetingParticipant> participants = participantRepository.findByMeetingId(meeting.getId());
        List<MeetingParticipant> activeParticipants = participants.stream()
                .filter(p -> p.getLeftAt() == null)
                .toList();

        if (activeParticipants.isEmpty()) {
            closeMeeting(meeting);
            return;
        }

        // Check if only the host is left active
        boolean onlyHostActive = activeParticipants.size() == 1
                && activeParticipants.get(0).getUserId().equals(meeting.getStartedBy());

        if (onlyHostActive) {
            // Check if any other user ever joined the call
            boolean anyOtherJoined = participants.stream()
                    .anyMatch(p -> !p.getUserId().equals(meeting.getStartedBy()) && p.getJoinedAt() != null);

            // Check if everyone else in the group rejected or left
            List<GroupMember> groupMembers = groupMemberRepository.findAllByGroupId(meeting.getGroupId());
            Set<Long> respondedUserIds = participants.stream()
                    .map(MeetingParticipant::getUserId)
                    .collect(Collectors.toSet());
            boolean allResponded = groupMembers.stream()
                    .map(GroupMember::getUserId)
                    .allMatch(respondedUserIds::contains);

            // Close the meeting if someone joined and left, or everyone rejected leaving only the host
            if (anyOtherJoined || allResponded) {
                closeMeeting(meeting);
            }
        }
    }

    // Helper: Notifies active participants of joins/leaves
    private void notifyParticipantsOfChange(Meeting meeting, String type, Long actionUserId) {
        List<MeetingParticipant> activeParticipants =
                participantRepository.findByMeetingId(meeting.getId())
                        .stream()
                        .filter(p -> p.getLeftAt() == null)
                        .toList();

        List<Long> activeParticipantIds = activeParticipants.stream()
                .map(MeetingParticipant::getUserId)
                .toList();

        for (MeetingParticipant participant : activeParticipants) {
            SocketMessageDto socket = new SocketMessageDto();
            socket.setType(type);
            socket.setMeetingId(meeting.getId());
            socket.setGroupId(meeting.getGroupId());
            socket.setHostUserId(meeting.getStartedBy());
            socket.setSenderId(actionUserId);
            socket.setParticipantIds(activeParticipantIds);

            messagingTemplate.convertAndSend(
                    "/topic/meeting/" + participant.getUserId(),
                    socket
            );
        }
    }

    // Helper: Notifies active participants of media status changes (mute/speaker)
    private void notifyParticipantsOfMediaChange(Meeting meeting, String type, Long actionUserId, Boolean muted, Boolean speaker) {
        List<MeetingParticipant> activeParticipants =
                participantRepository.findByMeetingId(meeting.getId())
                        .stream()
                        .filter(p -> p.getLeftAt() == null)
                        .toList();

        for (MeetingParticipant participant : activeParticipants) {
            SocketMessageDto socket = new SocketMessageDto();
            socket.setType(type);
            socket.setMeetingId(meeting.getId());
            socket.setGroupId(meeting.getGroupId());
            socket.setHostUserId(meeting.getStartedBy());
            socket.setSenderId(actionUserId);
            socket.setMuted(muted);
            socket.setSpeaker(speaker);

            messagingTemplate.convertAndSend(
                    "/topic/meeting/" + participant.getUserId(),
                    socket
            );
        }
    }

    // Helper: Notifies all group members that the meeting has ended
    private void notifyGroupMembersOfEnd(Meeting meeting) {
        List<GroupMember> members = groupMemberRepository.findAllByGroupId(meeting.getGroupId());
        for (GroupMember member : members) {
            SocketMessageDto socket = new SocketMessageDto();
            socket.setType("MEETING_ENDED");
            socket.setMeetingId(meeting.getId());
            socket.setGroupId(meeting.getGroupId());
            socket.setHostUserId(meeting.getStartedBy());

            messagingTemplate.convertAndSend(
                    "/topic/meeting/" + member.getUserId(),
                    socket
            );
        }
    }
}