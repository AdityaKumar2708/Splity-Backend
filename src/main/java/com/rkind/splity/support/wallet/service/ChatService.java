package com.rkind.splity.support.wallet.service;

import com.rkind.splity.support.wallet.dto.AIRequest;
import com.rkind.splity.support.wallet.dto.AIResponse;
import com.rkind.splity.support.wallet.dto.ChatMessageRequest;
import com.rkind.splity.support.wallet.dto.ChatMessageResponse;
import com.rkind.splity.support.wallet.entity.SupportMessage;
import com.rkind.splity.support.wallet.entity.SupportTicket;
import com.rkind.splity.support.wallet.enums.SenderType;
import com.rkind.splity.support.wallet.repository.SupportMessageRepository;
import com.rkind.splity.support.wallet.repository.SupportTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.rkind.splity.support.wallet.service.ConversationMemoryService;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ChatService {

    private final SupportTicketRepository ticketRepository;
    private final SupportMessageRepository messageRepository;
    private final AIService aiService;
    private final ConversationMemoryService memoryService;

    public ChatService(
            SupportTicketRepository ticketRepository,
            SupportMessageRepository messageRepository,
            AIService aiService,
            ConversationMemoryService memoryService
    ) {
        this.ticketRepository = ticketRepository;
        this.messageRepository = messageRepository;
        this.aiService = aiService;
        this.memoryService = memoryService;
    }

    public ChatMessageResponse sendMessage(ChatMessageRequest request) {

        AIRequest aiRequest = new AIRequest();
        aiRequest.setUserId(request.getUserId());
        aiRequest.setTicketNumber(request.getTicketNumber());
        aiRequest.setMessage(request.getMessage());

        memoryService.saveUserMessage(
                request.getUserId(),
                request.getTicketNumber(),
                request.getMessage()
        );

        AIResponse aiResponse = aiService.process(aiRequest);

        memoryService.saveAIMessage(
                request.getUserId(),
                request.getTicketNumber(),
                aiResponse.getReply()
        );

        ChatMessageResponse response = new ChatMessageResponse();
        response.setSuccess(aiResponse.isSuccess());
        response.setReply(aiResponse.getReply());
        response.setCreateTicket(aiResponse.isCreateTicket());

        if (request.getTicketNumber() != null &&
                !request.getTicketNumber().isBlank()) {

            SupportTicket ticket = ticketRepository
                    .findByTicketNumber(request.getTicketNumber())
                    .orElseThrow(() ->
                            new RuntimeException("Ticket not found"));

            // Save User Message
            SupportMessage userMessage = new SupportMessage();
            userMessage.setTicket(ticket);
            userMessage.setSender(SenderType.USER);
            userMessage.setMessage(request.getMessage());

            messageRepository.save(userMessage);

            // Save Bot Message
            SupportMessage botMessage = new SupportMessage();
            botMessage.setTicket(ticket);
            botMessage.setSender(SenderType.BOT);
            botMessage.setMessage(response.getReply());

            messageRepository.save(botMessage);

            ticket.setLastMessage(request.getMessage());

            ticketRepository.save(ticket);
        }

        return response;
    }

    public List<ChatMessageResponse> getMessages(String ticketNumber) {

        List<SupportMessage> messages =
                messageRepository.findByTicket_TicketNumberOrderByCreatedAtAsc(ticketNumber);

        List<ChatMessageResponse> responseList = new ArrayList<>();

        for (SupportMessage message : messages) {

            ChatMessageResponse response = new ChatMessageResponse();

            response.setSender(message.getSender());
            response.setReply(message.getMessage());
            response.setCreatedAt(message.getCreatedAt());

            responseList.add(response);
        }

        return responseList;
    }

}