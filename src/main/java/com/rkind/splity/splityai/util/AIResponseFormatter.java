package com.rkind.splity.splityai.util;

import org.springframework.stereotype.Component;

@Component
public class AIResponseFormatter {

    public String format(String response) {

        if (response == null || response.trim().isEmpty()) {
            return "Sorry, I couldn't generate a response.";
        }

        String formatted = response.trim();

        // Normalize line breaks
        formatted = formatted.replace("\r\n", "\n");

        // Remove excessive blank lines
        while (formatted.contains("\n\n\n")) {
            formatted = formatted.replace("\n\n\n", "\n\n");
        }

        // Remove unnecessary spaces
        formatted = formatted.replaceAll("[ \\t]+", " ");

        // Trim each line
        StringBuilder builder = new StringBuilder();

        String[] lines = formatted.split("\n");

        for (String line : lines) {
            builder.append(line.trim()).append("\n");
        }

        return builder.toString().trim();
    }

}