package com.rkind.splity.wallet.controller;

import com.rkind.splity.wallet.dto.*;
import com.rkind.splity.wallet.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @PostMapping("/activate")
    public ResponseEntity<WalletResponse> activateWallet (@RequestBody WalletActivationRequest request) {
        return ResponseEntity.ok(walletService.activateWallet(request));
    }

    @PostMapping("/create-order")
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(walletService.createOrder(request));
    }

    @PostMapping("/verify_payment")
    public ResponseEntity<VerifyPaymentResponse> verifyPayment (@RequestBody VerifyPaymentRequest request) {
        return ResponseEntity.ok(walletService.verifyPayment(request));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<WalletDetailsResponse> getWallet(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                walletService.getWallet(userId)
        );
    }

    @PostMapping("/send-email-otp")
    public ResponseEntity<SendEmailOtpResponse> sendEmailOtp(
            @RequestBody SendEmailOtpRequest request) {

        walletService.sendEmailOtp(request);

        SendEmailOtpResponse response =
                new SendEmailOtpResponse();

        response.setSuccess(true);
        response.setMessage("OTP Sent Successfully");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/verify-email-otp")
    public ResponseEntity<VerifyEmailOtpResponse> verifyEmailOtp(
            @RequestBody VerifyEmailOtpRequest request) {

        walletService.verifyEmailOtp(request);

        VerifyEmailOtpResponse response =
                new VerifyEmailOtpResponse();

        response.setSuccess(true);
        response.setMessage("OTP Verified");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/resend-email-otp")
    public ResponseEntity<SendEmailOtpResponse> resendEmailOtp(
            @RequestBody SendEmailOtpRequest request) {

        walletService.resendEmailOtp(request);

        SendEmailOtpResponse response =
                new SendEmailOtpResponse();

        response.setSuccess(true);
        response.setMessage("OTP Resent Successfully");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/payment-status/{orderId}")
    public ResponseEntity<PaymentStatusResponse>
    getPaymentStatus(
            @PathVariable String orderId) {

        return ResponseEntity.ok(
                walletService.getPaymentStatus(orderId)
        );

    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<WalletTransactionResponse>> getTransactionHistory(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                walletService.getTransactionHistory(userId)
        );
    }

    @GetMapping("/transactions/{userId}")
    public ResponseEntity<List<TransactionHistoryResponse>> getAllTransactions(
            @PathVariable Long userId) {

        System.out.println("================================");
        System.out.println("Transactions API Called");
        System.out.println("UserId = " + userId);
        System.out.println("================================");

        return ResponseEntity.ok(
                walletService.getAllTransactions(userId)
        );
    }


}

