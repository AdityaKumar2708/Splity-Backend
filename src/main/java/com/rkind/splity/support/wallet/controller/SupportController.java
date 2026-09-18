package com.rkind.splity.support.wallet.controller;

import com.rkind.splity.support.wallet.dto.*;
import com.rkind.splity.support.wallet.service.ChatService;
import com.rkind.splity.support.wallet.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support/wallet")
public class SupportController {

    private final ChatService chatService;
    private final TicketService ticketService;

    public SupportController(ChatService chatService,
                             TicketService ticketService) {

        this.chatService = chatService;
        this.ticketService = ticketService;
    }

    @PostMapping("/chat/start")
    public ResponseEntity<ChatStartResponse> startChat(
            @RequestBody StartChatRequest request) {

        ChatStartResponse response = new ChatStartResponse(
                true,
                "Hi 👋 Welcome to Splity Wallet Support. How can I help you today?"
        );

        return ResponseEntity.ok(response);
    }

    @PostMapping("/chat/message")
    public ResponseEntity<ChatMessageResponse> sendMessage(
            @RequestBody ChatMessageRequest request) {

        return ResponseEntity.ok(
                chatService.sendMessage(request)
        );
    }

    @PostMapping("/ticket/create")
    public ResponseEntity<TicketResponse> createTicket(
            @RequestBody CreateTicketRequest request) {

        return ResponseEntity.ok(
                ticketService.createTicket(request)
        );
    }

    @GetMapping("/tickets/{userId}")
    public ResponseEntity<List<TicketResponse>> getUserTickets(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                ticketService.getUserTickets(userId)
        );
    }

    @GetMapping("/ticket/{ticketNumber}")
    public ResponseEntity<TicketResponse> getTicket(
            @PathVariable String ticketNumber) {

        return ResponseEntity.ok(
                ticketService.getTicket(ticketNumber)
        );
    }

    @GetMapping("/ticket/{ticketNumber}/messages")
    public ResponseEntity<List<ChatMessageResponse>> getMessages(
            @PathVariable String ticketNumber) {

        return ResponseEntity.ok(
                chatService.getMessages(ticketNumber)
        );
    }

}