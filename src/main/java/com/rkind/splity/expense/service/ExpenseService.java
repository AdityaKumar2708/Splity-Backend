package com.rkind.splity.expense.service;

import com.rkind.splity.expense.dto.ExpenseResponse;
import com.rkind.splity.expense.dto.SaveExpenseRequest;
import com.rkind.splity.expense.entity.*;
import com.rkind.splity.expense.repository.ExpenseRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import com.rkind.splity.wallet.entity.TransactionStatus;
import com.rkind.splity.wallet.entity.TransactionType;
import com.rkind.splity.wallet.entity.Wallet;
import com.rkind.splity.wallet.entity.WalletTransaction;
import com.rkind.splity.wallet.repository.WalletRepository;
import com.rkind.splity.wallet.repository.WalletTransactionRepository;

import java.util.UUID;

import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final WalletRepository walletRepository;

    private final WalletTransactionRepository walletTransactionRepository;

    public ExpenseService(
            ExpenseRepository expenseRepository,
            WalletRepository walletRepository,
            WalletTransactionRepository walletTransactionRepository
    ) {
        this.expenseRepository = expenseRepository;
        this.walletRepository = walletRepository;
        this.walletTransactionRepository = walletTransactionRepository;
    }


    @Transactional
    public ExpenseResponse saveExpense(
            SaveExpenseRequest request
    ) {

        System.out.println("========= EXPENSE API HIT =========");
        System.out.println(request.getMerchantName());
        System.out.println(request.getAmount());

        expenseRepository.findByTransactionId(
                request.getTransactionId()
        ).ifPresent(expense -> {

            throw new RuntimeException(
                    "Transaction already exists."
            );

        });

        ExpenseTransaction expense =
                new ExpenseTransaction();

        expense.setUserId(
                request.getUserId()
        );

        expense.setGroupId(
                request.getGroupId()
        );

        expense.setMerchantName(
                request.getMerchantName()
        );

        expense.setAmount(
                request.getAmount()
        );

        expense.setPaymentMethod(
                PaymentMethod.valueOf(
                        request.getPaymentMethod().toUpperCase()
                )
        );

        expense.setPaymentApp(
                PaymentApp.valueOf(
                        request.getPaymentApp().toUpperCase()
                )
        );

        expense.setStatus(
                ExpenseStatus.valueOf(
                        request.getStatus().toUpperCase()
                )
        );

        if (request.getTransactionId() == null
                || request.getTransactionId().isBlank()) {

            expense.setTransactionId(
                    UUID.randomUUID().toString()
            );

        } else {

            expense.setTransactionId(
                    request.getTransactionId()
            );

        }

        expense.setCategory(
                request.getCategory()
        );

        expense.setNotes(
                request.getNotes()
        );

        expense.setSplitDone(false);

        expense =
                expenseRepository.save(expense);

        Wallet wallet = walletRepository
                .findByUserId(request.getUserId())
                .orElse(null);

        if (wallet != null) {

            WalletTransaction transaction =
                    new WalletTransaction();

            transaction.setWallet(wallet);

            transaction.setAmount(request.getAmount());

            transaction.setType(TransactionType.DEBIT);

            transaction.setStatus(TransactionStatus.SUCCESS);

            transaction.setDescription(
                    request.getMerchantName()
            );

            transaction.setReferenceNumber(
                    expense.getTransactionId()
            );


         /*
         transaction.setPaymentChannel("UPI");

          transaction.setPaymentApp(
            request.getPaymentApp()
            );

          transaction.setTransactionTitle(
            request.getMerchantName()
         );
          */

            walletTransactionRepository.save(transaction);
        }

        ExpenseResponse response =
                new ExpenseResponse();

        response.setId(
                expense.getId()
        );

        response.setUserId(
                expense.getUserId()
        );

        response.setGroupId(
                expense.getGroupId()
        );

        response.setMerchantName(
                expense.getMerchantName()
        );

        response.setAmount(
                expense.getAmount()
        );

        response.setPaymentMethod(
                expense.getPaymentMethod().name()
        );

        response.setPaymentApp(
                expense.getPaymentApp().name()
        );

        response.setTransactionId(
                expense.getTransactionId()
        );

        response.setStatus(
                expense.getStatus().name()
        );

        response.setCategory(
                expense.getCategory()
        );

        response.setNotes(
                expense.getNotes()
        );

        response.setCreatedAt(
                expense.getCreatedAt()
        );

        return response;

    }

    public List<ExpenseResponse> getUserHistory(
            Long userId
    ) {

        return expenseRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(expense -> {

                    ExpenseResponse response =
                            new ExpenseResponse();

                    response.setId(expense.getId());

                    response.setUserId(
                            expense.getUserId()
                    );

                    response.setGroupId(
                            expense.getGroupId()
                    );

                    response.setMerchantName(
                            expense.getMerchantName()
                    );

                    response.setAmount(
                            expense.getAmount()
                    );

                    response.setPaymentMethod(
                            expense.getPaymentMethod().name()
                    );

                    response.setPaymentApp(
                            expense.getPaymentApp().name()
                    );

                    response.setTransactionId(
                            expense.getTransactionId()
                    );

                    response.setStatus(
                            expense.getStatus().name()
                    );

                    response.setCategory(
                            expense.getCategory()
                    );

                    response.setNotes(
                            expense.getNotes()
                    );

                    response.setCreatedAt(
                            expense.getCreatedAt()
                    );

                    return response;

                })
                .toList();

    }

    public List<ExpenseResponse> getGroupHistory(
            Long groupId
    ) {

        return expenseRepository
                .findByGroupIdOrderByCreatedAtDesc(groupId)
                .stream()
                .map(expense -> {

                    ExpenseResponse response =
                            new ExpenseResponse();

                    response.setId(expense.getId());

                    response.setUserId(
                            expense.getUserId()
                    );

                    response.setGroupId(
                            expense.getGroupId()
                    );

                    response.setMerchantName(
                            expense.getMerchantName()
                    );

                    response.setAmount(
                            expense.getAmount()
                    );

                    response.setPaymentMethod(
                            expense.getPaymentMethod().name()
                    );

                    response.setPaymentApp(
                            expense.getPaymentApp().name()
                    );

                    response.setTransactionId(
                            expense.getTransactionId()
                    );

                    response.setStatus(
                            expense.getStatus().name()
                    );

                    response.setCategory(
                            expense.getCategory()
                    );

                    response.setNotes(
                            expense.getNotes()
                    );

                    response.setCreatedAt(
                            expense.getCreatedAt()
                    );

                    return response;

                })
                .toList();

    }

}