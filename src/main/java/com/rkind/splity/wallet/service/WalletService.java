package com.rkind.splity.wallet.service;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.razorpay.Utils;
import com.rkind.splity.entity.User;
import com.rkind.splity.repository.UserRepository;
import com.rkind.splity.wallet.dto.*;
import com.rkind.splity.wallet.entity.*;
import com.rkind.splity.wallet.repository.PaymentOrderRepository;
import com.rkind.splity.wallet.repository.WalletEmailOtpRepository;
import com.rkind.splity.wallet.repository.WalletRepository;
import com.rkind.splity.wallet.repository.WalletTransactionRepository;
import com.rkind.splity.wallet.util.WalletNumberGenerator;
import jakarta.transaction.Transactional;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;
    private final RazorpayClient razorpayClient;
    private final WalletTransactionRepository walletTransactionRepository;
    private final WalletEmailOtpRepository walletEmailOtpRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PaymentOrderRepository paymentOrderRepository;

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    public WalletService(
            WalletRepository walletRepository,
            UserRepository userRepository,
            RazorpayClient razorpayClient,
            WalletTransactionRepository walletTransactionRepository,
            WalletEmailOtpRepository walletEmailOtpRepository,
            EmailService emailService,
            BCryptPasswordEncoder passwordEncoder,
            PaymentOrderRepository paymentOrderRepository
    ) {

        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
        this.razorpayClient = razorpayClient;
        this.walletTransactionRepository = walletTransactionRepository;
        this.walletEmailOtpRepository = walletEmailOtpRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
        this.paymentOrderRepository = paymentOrderRepository;
    }


    @Transactional
    public WalletResponse activateWallet(WalletActivationRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (walletRepository.existsByUserId(user.getId())) {
            throw new RuntimeException("Wallet already activated");
        }

        WalletEmailOtp otp = walletEmailOtpRepository
                .findTopByUserIdOrderByCreatedAtDesc(user.getId())
                .orElseThrow(() ->
                        new RuntimeException("Email OTP not found"));

        if (!otp.getVerified()) {
            throw new RuntimeException("Please verify your email OTP first");
        }

        if (!otp.getEmail().equalsIgnoreCase(request.getEmail())) {
            throw new RuntimeException("Email does not match verified email");
        }

        Wallet wallet = new Wallet();

        wallet.setUser(user);

        wallet.setWalletNumber(
                WalletNumberGenerator.generate()
        );

        wallet.setBalance(BigDecimal.ZERO);

        wallet.setStatus(WalletStatus.ACTIVE);

        wallet.setEmail(request.getEmail());

        wallet.setEmailVerified(true);

        wallet.setWalletPinHash(
                passwordEncoder.encode(request.getWalletPin())
        );

        wallet.setTrustedDeviceId(
                request.getDeviceId()
        );

        wallet.setWalletVerified(true);

        wallet = walletRepository.save(wallet);

        WalletResponse response = new WalletResponse();

        response.setWalletId(wallet.getId());

        response.setWalletNumber(wallet.getWalletNumber());

        response.setBalance(wallet.getBalance());

        response.setStatus(wallet.getStatus().name());

        return response;
    }

    public CreateOrderResponse createOrder(CreateOrderRequest request) {

        Wallet wallet = walletRepository.findByUserId(request.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Wallet not found"));

        try {

            JSONObject orderRequest = new JSONObject();

            orderRequest.put(
                    "amount",
                    request.getAmount()
                            .multiply(new BigDecimal("100"))
                            .intValue()
            );

            orderRequest.put("currency", "INR");

            orderRequest.put(
                    "receipt",
                    "wallet_" + System.currentTimeMillis()
            );

            Order order = razorpayClient.orders.create(orderRequest);

            PaymentOrder paymentOrder = new PaymentOrder();

            paymentOrder.setUserId(request.getUserId());

            paymentOrder.setAmount(request.getAmount());

            paymentOrder.setRazorpayOrderId(
                    order.get("id").toString()
            );

            paymentOrder.setStatus(
                    PaymentOrderStatus.CREATED
            );

            paymentOrderRepository.save(paymentOrder);

            // ==========================

            CreateOrderResponse response =
                    new CreateOrderResponse();

            response.setOrderId(
                    order.get("id").toString()
            );

            response.setAmount(
                    ((Number) order.get("amount")).longValue()
            );

            response.setCurrency(
                    order.get("currency").toString()
            );

            response.setKeyId(keyId);

            return response;

        } catch (Exception e) {

            throw new RuntimeException(
                    "Unable to create Razorpay Order",
                    e
            );

        }

    }

    @Transactional
    public VerifyPaymentResponse verifyPayment(VerifyPaymentRequest request) {

        try {

            JSONObject options = new JSONObject();

            options.put("razorpay_order_id",
                    request.getRazorpayOrderId());

            options.put("razorpay_payment_id",
                    request.getRazorpayPaymentId());

            options.put("razorpay_signature",
                    request.getRazorpaySignature());

            boolean valid =
                    Utils.verifyPaymentSignature(options, keySecret);

            if (!valid) {
                throw new RuntimeException("Invalid payment signature");
            }

            PaymentOrder paymentOrder =
                    paymentOrderRepository
                            .findByRazorpayOrderId(
                                    request.getRazorpayOrderId())
                            .orElseThrow(() ->
                                    new RuntimeException("Payment Order not found"));

            if (paymentOrder.getStatus() == PaymentOrderStatus.SUCCESS) {

                throw new RuntimeException("Payment already processed");

            }

            Wallet wallet =
                    walletRepository.findByUserId(request.getUserId())
                            .orElseThrow(() ->
                                    new RuntimeException("Wallet not found"));

            wallet.setBalance(
                    wallet.getBalance().add(paymentOrder.getAmount())
            );

            walletRepository.save(wallet);

            paymentOrder.setStatus(
                    PaymentOrderStatus.SUCCESS
            );

            paymentOrder.setRazorpayPaymentId(
                    request.getRazorpayPaymentId()
            );

            paymentOrder.setRazorpaySignature(
                    request.getRazorpaySignature()
            );

            paymentOrder.setCompletedAt(
                    LocalDateTime.now()
            );

            paymentOrderRepository.save(paymentOrder);

            WalletTransaction transaction =
                    new WalletTransaction();

            transaction.setWallet(wallet);

            transaction.setAmount(
                    paymentOrder.getAmount()
            );

            transaction.setType(
                    TransactionType.CREDIT
            );

            transaction.setStatus(
                    TransactionStatus.SUCCESS
            );

            transaction.setDescription(
                    "Wallet Top-up"
            );

            transaction.setRazorpayOrderId(
                    request.getRazorpayOrderId()
            );

            transaction.setRazorpayPaymentId(
                    request.getRazorpayPaymentId()
            );

            transaction.setRazorpaySignature(
                    request.getRazorpaySignature()
            );

            transaction.setReferenceNumber(
                    UUID.randomUUID().toString()
            );

            walletTransactionRepository.save(transaction);

            VerifyPaymentResponse response =
                    new VerifyPaymentResponse();

            response.setSuccess(true);

            response.setMessage(
                    "Money Added Successfully"
            );

            response.setWalletBalance(
                    wallet.getBalance()
            );

            return response;

        } catch (Exception e) {

            throw new RuntimeException(
                    e.getMessage(),
                    e
            );

        }

    }

    public WalletDetailsResponse getWallet(Long userId) {

        System.out.println("Wallet Request UserId = " + userId);

        WalletDetailsResponse response = new WalletDetailsResponse();

        Wallet wallet = walletRepository
                .findByUserId(userId)
                .orElse(null);

        if (wallet == null) {

            response.setWalletId(null);
            response.setWalletNumber(null);
            response.setBalance(BigDecimal.ZERO);
            response.setStatus("NOT_ACTIVATED");

            return response;
        }

        response.setWalletId(wallet.getId());
        response.setWalletNumber(wallet.getWalletNumber());
        response.setBalance(wallet.getBalance());
        response.setStatus(wallet.getStatus().name());

        return response;
    }

    public void sendEmailOtp(SendEmailOtpRequest request) {

        System.out.println("========== OTP REQUEST ==========");
        System.out.println("UserId = " + request.getUserId());
        System.out.println("Email = " + request.getEmail());

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String otp = String.format("%06d", new Random().nextInt(999999));

        WalletEmailOtp walletOtp = new WalletEmailOtp();

        walletOtp.setUserId(user.getId());
        walletOtp.setEmail(request.getEmail());
        walletOtp.setOtp(otp);
        walletOtp.setVerified(false);
        walletOtp.setExpiresAt(LocalDateTime.now().plusMinutes(5));

        walletEmailOtpRepository.save(walletOtp);

        emailService.sendWalletOtp(
                request.getEmail(),
                otp
        );
    }

    public void verifyEmailOtp(VerifyEmailOtpRequest request) {

        WalletEmailOtp otp =
                walletEmailOtpRepository.findByUserIdAndEmailAndOtp(
                        request.getUserId(),
                        request.getEmail(),
                        request.getOtp()
                ).orElseThrow(() ->
                        new RuntimeException("Invalid OTP"));

        if (otp.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP Expired");
        }

        otp.setVerified(true);

        walletEmailOtpRepository.save(otp);
    }

    public void resendEmailOtp(SendEmailOtpRequest request) {

        sendEmailOtp(request);

    }

    public PaymentStatusResponse getPaymentStatus(
            String orderId) {

        PaymentOrder paymentOrder =
                paymentOrderRepository
                        .findByRazorpayOrderId(orderId)
                        .orElseThrow(() ->
                                new RuntimeException("Order not found"));

        PaymentStatusResponse response =
                new PaymentStatusResponse();

        response.setSuccess(true);

        response.setStatus(
                paymentOrder.getStatus().name()
        );

        response.setMessage(
                "Payment Status Loaded"
        );

        return response;

    }

    public List<WalletTransactionResponse> getTransactionHistory(Long userId) {

        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Wallet not found"));

        List<WalletTransaction> transactions =
                walletTransactionRepository.findByWalletOrderByCreatedAtDesc(wallet);

        List<WalletTransactionResponse> response = new ArrayList<>();

        for (WalletTransaction transaction : transactions) {

            WalletTransactionResponse item = new WalletTransactionResponse();

            item.setId(transaction.getId());
            item.setAmount(transaction.getAmount());
            item.setType(transaction.getType().name());
            item.setStatus(transaction.getStatus().name());
            item.setDescription(transaction.getDescription());
            item.setReferenceNumber(transaction.getReferenceNumber());
            item.setCreatedAt(transaction.getCreatedAt());

            response.add(item);
        }

        return response;
    }

    public List<TransactionHistoryResponse> getAllTransactions(Long userId) {



        Wallet wallet = walletRepository.findByUserId(userId)
                .orElseThrow(() ->
                        new RuntimeException("Wallet not found"));

        List<WalletTransaction> transactions =
                walletTransactionRepository
                        .findByWalletOrderByCreatedAtDesc(wallet);

        System.out.println("=================================");
        System.out.println("User ID = " + userId);
        System.out.println("Transactions Found = " + transactions.size());
        System.out.println("=================================");

        List<TransactionHistoryResponse> response =
                new ArrayList<>();

        for (WalletTransaction transaction : transactions) {

            TransactionHistoryResponse item =
                    new TransactionHistoryResponse();

            item.setId(transaction.getId());

            item.setTitle(
                    transaction.getDescription()
            );

            item.setAmount(
                    transaction.getAmount()
            );

            item.setType(
                    transaction.getType().name()
            );

            item.setStatus(
                    transaction.getStatus().name()
            );

            item.setReferenceNumber(
                    transaction.getReferenceNumber()
            );

            item.setCreatedAt(
                    transaction.getCreatedAt()
            );
            
            item.setPaymentChannel(
                    transaction.getPaymentChannel()
            );

            item.setPaymentApp(
                    transaction.getPaymentApp()
            );

            response.add(item);
        }

        return response;
    }
}