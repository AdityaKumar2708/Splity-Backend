package com.rkind.splity.wallet.service;

import org.springframework.beans.factory.annotation.Value;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.rkind.splity.wallet.dto.RazorpayWebhookRequest;
import com.rkind.splity.wallet.entity.*;
import com.rkind.splity.wallet.repository.WalletLedgerRepository;
import org.springframework.stereotype.Service;
import com.rkind.splity.wallet.repository.PaymentOrderRepository;
import com.rkind.splity.wallet.repository.WalletRepository;
import com.rkind.splity.wallet.repository.WalletTransactionRepository;
import jakarta.transaction.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PaymentWebhookService {

    private final PaymentOrderRepository paymentOrderRepository;
    private final WalletRepository walletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletLedgerRepository walletLedgerRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${razorpay.webhook.secret}")
    private String webhookSecret;

    public PaymentWebhookService(
            PaymentOrderRepository paymentOrderRepository,
            WalletRepository walletRepository,
            WalletTransactionRepository walletTransactionRepository,
            WalletLedgerRepository walletLedgerRepository
    ) {

        this.paymentOrderRepository = paymentOrderRepository;
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
        this.walletLedgerRepository = walletLedgerRepository;
    }

    @Transactional
    public void processWebhook(
            String payload,
            String signature) {

        try {

            boolean valid = Utils.verifyWebhookSignature(
                    payload,
                    signature,
                    webhookSecret
            );

            if (!valid) {
                throw new RuntimeException("Invalid Razorpay Webhook Signature");
            }



            RazorpayWebhookRequest request =
                    objectMapper.readValue(
                            payload,
                            RazorpayWebhookRequest.class
                    );

            if (request == null
                    || request.getPayload() == null
                    || request.getPayload().getPayment() == null
                    || request.getPayload().getPayment().getEntity() == null) {

                return;
            }

            String paymentId =
                    request.getPayload()
                            .getPayment()
                            .getEntity()
                            .getId();

            String orderId =
                    request.getPayload()
                            .getPayment()
                            .getEntity()
                            .getOrder_id();

            String paymentStatus =
                    request.getPayload()
                            .getPayment()
                            .getEntity()
                            .getStatus();

            // Only captured payments
            if (!"captured".equalsIgnoreCase(paymentStatus)) {
                return;
            }


            PaymentOrder paymentOrder =
                    paymentOrderRepository
                            .findByRazorpayOrderId(orderId)
                            .orElseThrow(() ->
                                    new RuntimeException("Payment Order not found"));

            // Already processed
            if (paymentOrder.getStatus() == PaymentOrderStatus.SUCCESS) {
                return;
            }


            Wallet wallet =
                    walletRepository
                            .findByUserId(paymentOrder.getUserId())
                            .orElseThrow(() ->
                                    new RuntimeException("Wallet not found"));

            // Save opening balance
            java.math.BigDecimal openingBalance =
                    wallet.getBalance();

            // Update wallet balance
            wallet.setBalance(
                    openingBalance.add(paymentOrder.getAmount())
            );

            walletRepository.save(wallet);


            paymentOrder.setStatus(PaymentOrderStatus.SUCCESS);

            paymentOrder.setRazorpayPaymentId(paymentId);

            paymentOrder.setCompletedAt(LocalDateTime.now());

            paymentOrderRepository.save(paymentOrder);


            WalletTransaction transaction =
                    new WalletTransaction();

            transaction.setWallet(wallet);

            transaction.setAmount(paymentOrder.getAmount());

            transaction.setType(TransactionType.CREDIT);

            transaction.setStatus(TransactionStatus.SUCCESS);

            transaction.setDescription("Wallet Top-up");

            transaction.setRazorpayOrderId(orderId);

            transaction.setRazorpayPaymentId(paymentId);

            transaction.setReferenceNumber(
                    UUID.randomUUID().toString()
            );

            walletTransactionRepository.save(transaction);


            WalletLedger ledger =
                    new WalletLedger();

            ledger.setWallet(wallet);

            ledger.setTransaction(transaction);

            ledger.setOpeningBalance(openingBalance);

            ledger.setAmount(paymentOrder.getAmount());

            ledger.setClosingBalance(wallet.getBalance());

            ledger.setType(TransactionType.CREDIT);

            walletLedgerRepository.save(ledger);

            System.out.println(
                    "Webhook Processed Successfully : " + paymentId
            );

        } catch (Exception e) {

            System.out.println("========== WEBHOOK ERROR ==========");
            e.printStackTrace();

            return;
        }
    }

}