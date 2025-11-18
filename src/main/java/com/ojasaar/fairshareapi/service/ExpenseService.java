package com.ojasaar.fairshareapi.service;

import com.ojasaar.fairshareapi.domain.model.Expense;
import com.ojasaar.fairshareapi.domain.model.Group;
import com.ojasaar.fairshareapi.domain.model.User;
import com.ojasaar.fairshareapi.dto.ExpenseDto;
import com.ojasaar.fairshareapi.mapper.ExpenseMapper;
import com.ojasaar.fairshareapi.repository.ExpenseRepo;
import com.ojasaar.fairshareapi.repository.GroupRepo;
import com.ojasaar.fairshareapi.repository.UserRepo;
import com.ojasaar.fairshareapi.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepo expenseRepo;
    private final UserRepo userRepo;
    private final GroupRepo groupRepo;
    private final ExpenseMapper expenseMapper;

    public ExpenseDto createExpense(ExpenseDto expense, String groupId) {
        User owner =  userRepo.getReferenceById(UserUtil.getUserIdfromContext());
        Group group = groupRepo.getReferenceById(groupId);

        Expense newExpense = Expense.builder()
                .description(expense.description())
                .amount(expense.amount())
                .group(group)
                .owner(owner)
                .build();

        newExpense = expenseRepo.save(newExpense);
        return expenseMapper.toExpenseDto(newExpense);
    }

    public List<ExpenseDto> getGroupExpenses(String groupId) {
        Group group = groupRepo.getReferenceById(groupId);
        List<Expense> expenses = expenseRepo.getAllByGroup(group);
        return expenseMapper.toExpenseDtoSet(expenses);
    }
}
