package com.rkind.splity.support.wallet.service;

import com.rkind.splity.support.wallet.dto.AssignTicketRequest;
import com.rkind.splity.support.wallet.dto.SupportReplyRequest;
import com.rkind.splity.support.wallet.entity.SupportMessage;
import com.rkind.splity.support.wallet.entity.SupportTicket;
import com.rkind.splity.support.wallet.enums.SenderType;
import com.rkind.splity.support.wallet.enums.TicketStatus;
import com.rkind.splity.support.wallet.repository.SupportMessageRepository;
import com.rkind.splity.support.wallet.repository.SupportTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class AdminSupportService {

    private final SupportTicketRepository ticketRepository;
    private final SupportMessageRepository messageRepository;

    public AdminSupportService(
            SupportTicketRepository ticketRepository,
            SupportMessageRepository messageRepository) {

        this.ticketRepository = ticketRepository;
        this.messageRepository = messageRepository;
    }

    public List<SupportTicket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public void assignTicket(AssignTicketRequest request) {

        SupportTicket ticket = ticketRepository
                .findByTicketNumber(request.getTicketNumber())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setAssignedSupportId(request.getSupportId());
        ticket.setStatus(TicketStatus.IN_PROGRESS);

        ticketRepository.save(ticket);
    }

    public void reply(SupportReplyRequest request) {

        SupportTicket ticket = ticketRepository
                .findByTicketNumber(request.getTicketNumber())
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        SupportMessage message = new SupportMessage();

        message.setTicket(ticket);
        message.setSender(SenderType.SUPPORT);
        message.setMessage(request.getMessage());

        messageRepository.save(message);

        ticket.setLastMessage(request.getMessage());

        ticketRepository.save(ticket);
    }

    public void closeTicket(String ticketNumber) {

        SupportTicket ticket = ticketRepository
                .findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        ticket.setStatus(TicketStatus.CLOSED);
        ticket.setClosedAt(LocalDateTime.now());

        ticketRepository.save(ticket);
    }
}