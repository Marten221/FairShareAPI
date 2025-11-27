package com.ojasaar.fairshareapi.controller;

import com.ojasaar.fairshareapi.dto.ExpenseDto;
import com.ojasaar.fairshareapi.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ExpenseController {
    private final ExpenseService expenseService;

    @PostMapping("/expense/{groupId}")
    public ResponseEntity<ExpenseDto> createExpense(@RequestBody ExpenseDto expense, @PathVariable String groupId) {
        return ResponseEntity.ok(expenseService.createExpense(expense, groupId));
    }

    @GetMapping("/expenses/{groupId}")
    public ResponseEntity<List<ExpenseDto>> getGroupExpenses(@PathVariable String groupId) {
        return ResponseEntity.ok(expenseService.getGroupExpenses(groupId));
    }
}
