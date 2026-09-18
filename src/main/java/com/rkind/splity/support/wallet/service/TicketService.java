package com.rkind.splity.support.wallet.service;

import com.rkind.splity.support.wallet.dto.CreateTicketRequest;
import com.rkind.splity.support.wallet.dto.TicketResponse;
import com.rkind.splity.support.wallet.entity.SupportTicket;
import com.rkind.splity.support.wallet.enums.TicketPriority;
import com.rkind.splity.support.wallet.enums.TicketStatus;
import com.rkind.splity.support.wallet.repository.SupportTicketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class TicketService {

    private final SupportTicketRepository ticketRepository;
    private final TicketNumberGenerator ticketNumberGenerator;

    public TicketService(
            SupportTicketRepository ticketRepository,
            TicketNumberGenerator ticketNumberGenerator
    ) {
        this.ticketRepository = ticketRepository;
        this.ticketNumberGenerator = ticketNumberGenerator;
    }

    public TicketResponse createTicket(CreateTicketRequest request) {

        SupportTicket ticket = new SupportTicket();

        ticket.setTicketNumber(ticketNumberGenerator.generateTicketNumber());
        ticket.setUserId(request.getUserId());
        ticket.setCategory(request.getCategory());
        ticket.setSubject(request.getSubject());
        ticket.setDescription(request.getDescription());

        ticket.setPriority(TicketPriority.MEDIUM);
        ticket.setStatus(TicketStatus.OPEN);

        ticket.setLastMessage(request.getDescription());

        ticketRepository.save(ticket);

        TicketResponse response = new TicketResponse();

        response.setTicketNumber(ticket.getTicketNumber());
        response.setStatus(ticket.getStatus());
        response.setCategory(ticket.getCategory());
        response.setPriority(ticket.getPriority());
        response.setCreatedAt(ticket.getCreatedAt());

        response.setMessage("Your support ticket has been created successfully.");

        return response;
    }

    public List<TicketResponse> getUserTickets(Long userId) {

        List<SupportTicket> tickets =
                ticketRepository.findByUserIdOrderByCreatedAtDesc(userId);

        List<TicketResponse> responseList = new ArrayList<>();

        for (SupportTicket ticket : tickets) {

            TicketResponse response = new TicketResponse();

            response.setTicketNumber(ticket.getTicketNumber());
            response.setStatus(ticket.getStatus());
            response.setSubject(ticket.getSubject());
            response.setDescription(ticket.getDescription());
            response.setLastMessage(ticket.getLastMessage());
            response.setCategory(ticket.getCategory());
            response.setPriority(ticket.getPriority());
            response.setCreatedAt(ticket.getCreatedAt());

            responseList.add(response);
        }

        return responseList;
    }

    public TicketResponse getTicket(String ticketNumber) {

        SupportTicket ticket = ticketRepository
                .findByTicketNumber(ticketNumber)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));

        TicketResponse response = new TicketResponse();

        response.setTicketNumber(ticket.getTicketNumber());
        response.setStatus(ticket.getStatus());
        response.setSubject(ticket.getSubject());
        response.setDescription(ticket.getDescription());
        response.setCategory(ticket.getCategory());
        response.setPriority(ticket.getPriority());
        response.setCreatedAt(ticket.getCreatedAt());

        return response;
    }

}