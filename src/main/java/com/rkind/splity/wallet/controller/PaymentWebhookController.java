package com.rkind.splity.wallet.controller;

import com.rkind.splity.wallet.dto.RazorpayWebhookRequest;
import com.rkind.splity.wallet.service.PaymentWebhookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentWebhookController {

    private final PaymentWebhookService paymentWebhookService;

    public PaymentWebhookController(
            PaymentWebhookService paymentWebhookService
    ) {
        this.paymentWebhookService = paymentWebhookService;
    }

    @PostMapping("/webhook")
    public ResponseEntity<String> receiveWebhook(
            @RequestBody String payload,
            @RequestHeader("X-Razorpay-Signature") String signature) {

        System.out.println("========== WEBHOOK RECEIVED ==========");
        System.out.println(payload);

        return ResponseEntity.ok("WEBHOOK HIT");
    }

    @GetMapping("/test")
    public ResponseEntity<String> test() {

        System.out.println("TEST API HIT");

        return ResponseEntity.ok("WORKING");
    }

}