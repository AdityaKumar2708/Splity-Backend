package com.rkind.splity.wallet.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWalletOtp(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);

        message.setSubject("Splity Wallet Verification");

        message.setText(
                "Dear User,\n\n" +
                        "Your Splity Wallet verification code is:\n\n" +
                        otp +
                        "\n\nThis OTP is valid for 5 minutes.\n\n" +
                        "If you did not request this, please ignore this email.\n\n" +
                        "Team Splity"
        );

        mailSender.send(message);
    }
}