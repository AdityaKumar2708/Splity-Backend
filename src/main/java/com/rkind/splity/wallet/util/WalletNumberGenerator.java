package com.rkind.splity.wallet.util;

import java.security.SecureRandom;

public class WalletNumberGenerator {

    private static final SecureRandom RANDOM = new SecureRandom();

    private WalletNumberGenerator() {
    }

    public static String generate() {

        StringBuilder builder = new StringBuilder("SPW");

        for (int i = 0; i < 10; i++) {
            builder.append(RANDOM.nextInt(10));
        }

        return builder.toString();
    }
}