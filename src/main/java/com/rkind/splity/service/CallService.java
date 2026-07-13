package com.rkind.splity.service;

import com.rkind.splity.dto.call.CallRequestDto;
import com.rkind.splity.dto.call.CallResponseDto;
import com.rkind.splity.entity.User;
import com.rkind.splity.repository.UserRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CallService {

    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepository;

    public CallService(SimpMessagingTemplate messagingTemplate,
                       UserRepository userRepository) {

        this.messagingTemplate = messagingTemplate;
        this.userRepository = userRepository;
    }

    public void requestCall(CallRequestDto request) {

        User caller = userRepository.findById(request.getCallerId())
                .orElseThrow(() -> new RuntimeException("Caller not found"));

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        CallResponseDto response = new CallResponseDto();

        response.setCallerId(caller.getId());
        response.setReceiverId(receiver.getId());

        response.setCallerName(caller.getDisplayName());
        response.setReceiverName(receiver.getDisplayName());

        response.setGroupId(request.getGroupId());
        response.setCallType(request.getCallType());

        response.setStatus("RINGING");

        messagingTemplate.convertAndSend(
                "/topic/call/" + receiver.getId(),
                response
        );
    }
}