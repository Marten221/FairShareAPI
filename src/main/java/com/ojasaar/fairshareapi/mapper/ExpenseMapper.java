package com.ojasaar.fairshareapi.mapper;

import com.ojasaar.fairshareapi.domain.model.Expense;
import com.ojasaar.fairshareapi.dto.ExpenseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ExpenseMapper {
    private final UserMapper userMapper;

    public ExpenseDto toExpenseDto(Expense expense) {
        if (expense == null) {
            return null;
        }
        return new ExpenseDto(expense.getId(),
                expense.getDescription(),
                expense.getAmount(),
                expense.getTimestamp(),
                userMapper.toDto(expense.getOwner()));
    }

    public List<ExpenseDto> toExpenseDtoSet(List<Expense> expenses) {
        if (expenses == null) {
            return List.of();
        }
        return expenses.stream()
                .map(this::toExpenseDto)
                .collect(Collectors.toList());
    }
}
