package com.rkind.splity.splityai.service;

import com.rkind.splity.splityai.enums.SplityIntent;
import org.springframework.stereotype.Service;

@Service
public class SplityIntentService {

    public SplityIntent detectIntent(String message) {

        if (message == null || message.trim().isEmpty()) {
            return SplityIntent.UNKNOWN;
        }

        String text = message.toLowerCase().trim();

        // Greetings
        if (text.matches(".*\\b(hi|hello|hey|hii|good morning|good afternoon|good evening)\\b.*")) {
            return SplityIntent.GREETING;
        }

        // Wallet
        if (contains(text,
                "wallet",
                "money",
                "balance",
                "transaction",
                "deposit",
                "withdraw",
                "payment")) {

            return SplityIntent.WALLET;
        }

        // Split
        if (contains(text,
                "split",
                "expense",
                "expenses",
                "bill",
                "settlement",
                "owe",
                "paid")) {

            return SplityIntent.SPLIT;
        }

        // Travel
        if (contains(text,
                "train",
                "flight",
                "bus",
                "travel",
                "hotel",
                "booking",
                "ticket")) {

            return SplityIntent.TRAVEL;
        }

        // Group
        if (contains(text,
                "group",
                "member",
                "invite",
                "join",
                "leave")) {

            return SplityIntent.GROUP;
        }

        // Profile
        if (contains(text,
                "profile",
                "name",
                "photo",
                "dp",
                "account")) {

            return SplityIntent.PROFILE;
        }

        // Notification
        if (contains(text,
                "notification",
                "notifications",
                "alert")) {

            return SplityIntent.NOTIFICATION;
        }

        // Settings
        if (contains(text,
                "setting",
                "settings",
                "theme",
                "language")) {

            return SplityIntent.SETTINGS;
        }

        // Help
        if (contains(text,
                "help",
                "support",
                "guide")) {

            return SplityIntent.HELP;
        }

        // Navigation
        if (contains(text,
                "open",
                "go to",
                "navigate",
                "take me")) {

            return SplityIntent.APP_NAVIGATION;
        }

        return SplityIntent.GENERAL_CHAT;
    }

    private boolean contains(String text, String... keywords) {

        for (String keyword : keywords) {

            if (text.contains(keyword.toLowerCase())) {
                return true;
            }

        }

        return false;
    }

}