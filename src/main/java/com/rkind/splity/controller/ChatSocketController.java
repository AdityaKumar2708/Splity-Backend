package com.rkind.splity.controller;

import com.rkind.splity.dto.SocketMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class ChatSocketController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatSocketController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    /*
     * Group Chat
     */
    @MessageMapping("/group")
    public void sendGroupMessage(
            @Payload SocketMessageDto dto
    ) {

        messagingTemplate.convertAndSend(
                "/topic/group/" + dto.getGroupId(),
                dto
        );
    }

    /*
     * WebRTC Offer
     */
    @MessageMapping("/meeting.offer")
    public void sendOffer(
            @Payload SocketMessageDto dto
    ) {

        messagingTemplate.convertAndSend(
                "/topic/meeting/" + dto.getReceiverId(),
                dto
        );
    }

    /*
     * WebRTC Answer
     */
    @MessageMapping("/meeting.answer")
    public void sendAnswer(
            @Payload SocketMessageDto dto
    ) {

        messagingTemplate.convertAndSend(
                "/topic/meeting/" + dto.getReceiverId(),
                dto
        );
    }

    /*
     * ICE Candidate
     */
    @MessageMapping("/meeting.ice")
    public void sendIceCandidate(
            @Payload SocketMessageDto dto
    ) {

        messagingTemplate.convertAndSend(
                "/topic/meeting/" + dto.getReceiverId(),
                dto
        );
    }

}