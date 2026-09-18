package com.rkind.splity.splityai.provider;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DashboardContextProvider {

    public String buildContext(Long userId) {

        StringBuilder context = new StringBuilder();

        context.append("========== SPLITY APPLICATION ==========\n");
        context.append("Application : Splity\n");
        context.append("AI Assistant : Splity AI\n");
        context.append("Version : 1.0\n");
        context.append("Current Time : ").append(LocalDateTime.now()).append("\n\n");

        context.append("========== USER ==========\n");
        context.append("User ID : ").append(userId).append("\n");
        context.append("Authentication : Logged In\n\n");

        context.append("========== AVAILABLE MODULES ==========\n");
        context.append("- Dashboard\n");
        context.append("- Wallet\n");
        context.append("- Split Expenses\n");
        context.append("- Groups\n");
        context.append("- Travel\n");
        context.append("- Profile\n");
        context.append("- Notifications\n");
        context.append("- Settings\n\n");

        context.append("========== AI CAPABILITIES ==========\n");
        context.append("- General Conversation\n");
        context.append("- App Navigation\n");
        context.append("- Wallet Guidance\n");
        context.append("- Split Expense Guidance\n");
        context.append("- Travel Guidance\n");
        context.append("- Programming Help\n");
        context.append("- Educational Questions\n");
        context.append("- Image Understanding\n");
        context.append("- Voice Chat (Future)\n\n");

        context.append("========== IMPORTANT ==========\n");
        context.append("- Never invent application features.\n");
        context.append("- Use only supported Splity features.\n");
        context.append("- If a feature is unavailable, clearly tell the user.\n");
        context.append("- Maintain conversation context.\n");
        context.append("- Keep answers concise unless detailed information is requested.\n\n");

        context.append("========== FUTURE DYNAMIC CONTEXT ==========\n");
        context.append("- User Name\n");
        context.append("- Wallet Balance\n");
        context.append("- Current Group\n");
        context.append("- Pending Settlements\n");
        context.append("- Recent Transactions\n");
        context.append("- Upcoming Trips\n");
        context.append("- Active Notifications\n");

        return context.toString();
    }
}