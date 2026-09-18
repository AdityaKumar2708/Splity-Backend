package com.rkind.splity.support.wallet.controller;

import com.rkind.splity.support.wallet.dto.AssignTicketRequest;
import com.rkind.splity.support.wallet.dto.SupportReplyRequest;
import com.rkind.splity.support.wallet.entity.SupportTicket;
import com.rkind.splity.support.wallet.service.AdminSupportService;
import com.rkind.splity.support.wallet.service.WalletGeminiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/support/admin")
public class AdminSupportController {

    private final AdminSupportService adminSupportService;
    @Autowired
    private WalletGeminiService geminiService;

    public AdminSupportController(AdminSupportService adminSupportService) {
        this.adminSupportService = adminSupportService;
    }

    @GetMapping("/tickets")
    public ResponseEntity<List<SupportTicket>> getAllTickets() {

        return ResponseEntity.ok(
                adminSupportService.getAllTickets()
        );
    }


    @PostMapping("/assign")
    public ResponseEntity<String> assignTicket(
            @RequestBody AssignTicketRequest request) {

        adminSupportService.assignTicket(request);

        return ResponseEntity.ok("Ticket assigned successfully.");
    }


    @PostMapping("/reply")
    public ResponseEntity<String> reply(
            @RequestBody SupportReplyRequest request) {

        adminSupportService.reply(request);

        return ResponseEntity.ok("Reply sent successfully.");
    }


    @PostMapping("/close/{ticketNumber}")
    public ResponseEntity<String> closeTicket(
            @PathVariable String ticketNumber) {

        adminSupportService.closeTicket(ticketNumber);

        return ResponseEntity.ok("Ticket closed successfully.");
    }

    @GetMapping("/intent")
    public String intent(@RequestParam String msg){

        return geminiService.detectIntent(msg);

    }
}