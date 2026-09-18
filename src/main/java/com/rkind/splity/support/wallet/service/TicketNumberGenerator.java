package com.rkind.splity.support.wallet.service;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class TicketNumberGenerator {

    private final AtomicLong counter = new AtomicLong(1);

    private LocalDate currentDate = LocalDate.now();

    public synchronized String generateTicketNumber() {

        LocalDate today = LocalDate.now();

        if (!today.equals(currentDate)) {
            currentDate = today;
            counter.set(1);
        }

        String date = today.format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        long sequence = counter.getAndIncrement();

        return String.format("SPL-%s-%06d", date, sequence);
    }

}   