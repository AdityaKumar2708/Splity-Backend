package com.rkind.splity.support.wallet.enums;

public enum AIIntent {

    // Wallet
    WALLET_BALANCE,
    WALLET_ACTIVATION,
    WALLET_BLOCKED,
    WALLET_PIN_RESET,

    // Add Money
    ADD_MONEY,
    PAYMENT_PENDING,
    PAYMENT_FAILED,
    PAYMENT_SUCCESS,
    PAYMENT_STATUS,

    // Transactions
    TRANSACTION_HISTORY,
    TRANSACTION_DETAILS,

    // Refund
    REFUND_STATUS,

    // UPI / Bank
    UPI_ISSUE,
    BANK_ISSUE,

    // Security
    SECURITY,

    // Ticket
    CREATE_TICKET,
    TICKET_STATUS,

    // Human Support
    TRANSFER_TO_AGENT,

    // Greetings
    GREETING,
    THANK_YOU,

    // Unknown
    UNKNOWN

}