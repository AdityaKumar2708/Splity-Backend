package com.rkind.splity.expense.controller;

import com.rkind.splity.expense.dto.ExpenseResponse;
import com.rkind.splity.expense.dto.SaveExpenseRequest;
import com.rkind.splity.expense.service.ExpenseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expense")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(
            ExpenseService expenseService
    ) {
        this.expenseService = expenseService;
    }

    @PostMapping("/save")
    public ResponseEntity<ExpenseResponse> saveExpense(
            @RequestBody SaveExpenseRequest request
    ) {

        return ResponseEntity.ok(
                expenseService.saveExpense(request)
        );

    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<List<ExpenseResponse>> getUserHistory(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                expenseService.getUserHistory(userId)
        );

    }

    @GetMapping("/group/{groupId}")
    public ResponseEntity<List<ExpenseResponse>> getGroupHistory(
            @PathVariable Long groupId
    ) {

        return ResponseEntity.ok(
                expenseService.getGroupHistory(groupId)
        );

    }

}