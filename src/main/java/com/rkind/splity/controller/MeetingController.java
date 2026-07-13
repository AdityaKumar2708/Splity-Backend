package com.rkind.splity.controller;

import com.rkind.splity.dto.meeting.EndMeetingRequestDto;
import com.rkind.splity.dto.meeting.JoinMeetingRequestDto;
import com.rkind.splity.dto.meeting.MeetingResponseDto;
import com.rkind.splity.dto.meeting.RejectMeetingRequestDto;
import com.rkind.splity.dto.meeting.StartMeetingRequestDto;
import com.rkind.splity.service.MeetingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    /**
     * Host starts a new meeting.
     */
    @PostMapping("/start")
    public ResponseEntity<MeetingResponseDto> startMeeting(
            @RequestBody StartMeetingRequestDto request
    ) {

        return ResponseEntity.ok(
                meetingService.startMeeting(request)
        );
    }

    /**
     * User joins an active meeting.
     */
    @PostMapping("/join")
    public ResponseEntity<Void> joinMeeting(
            @RequestBody JoinMeetingRequestDto request
    ) {

        meetingService.joinMeeting(request);

        return ResponseEntity.ok().build();
    }

    /**
     * User leaves meeting.
     */
    @PostMapping("/leave")
    public ResponseEntity<Void> leaveMeeting(
            @RequestBody JoinMeetingRequestDto request
    ) {

        meetingService.leaveMeeting(request);

        return ResponseEntity.ok().build();
    }

    /**
     * Host ends the meeting for everyone.
     */
    @PostMapping("/end")
    public ResponseEntity<Void> endMeeting(
            @RequestBody EndMeetingRequestDto request
    ) {

        meetingService.endMeeting(request);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/reject")
    public ResponseEntity<Void> rejectMeeting(
            @RequestBody RejectMeetingRequestDto request
    ) {

        meetingService.rejectMeeting(request);

        return ResponseEntity.ok().build();
    }

    /**
     * Returns latest meeting information.
     */
    @GetMapping("/{meetingId}")
    public ResponseEntity<MeetingResponseDto> getMeeting(
            @PathVariable Long meetingId
    ) {

        return ResponseEntity.ok(
                meetingService.getMeeting(meetingId)
        );
    }
}