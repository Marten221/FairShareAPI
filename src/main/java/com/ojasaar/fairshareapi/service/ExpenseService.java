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
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.ojasaar.fairshareapi.util.UserUtil.hasAccessToGroup;

@Service
@RequiredArgsConstructor
public class ExpenseService {
    private final ExpenseRepo expenseRepo;
    private final UserRepo userRepo;
    private final GroupRepo groupRepo;
    private final ExpenseMapper expenseMapper;

    public ExpenseDto createExpense(ExpenseDto expense, String groupId) {
        String ownerId = UserUtil.getUserIdfromContext();
        User owner =  userRepo.getReferenceById(ownerId);
        Group group = groupRepo.getReferenceById(groupId);

        if (!hasAccessToGroup(ownerId, group)) {
            throw new AuthorizationDeniedException("You don't have access to this group");
        }

        Expense newExpense = Expense.builder()
                .description(expense.description())
                .amount((double) Math.round(expense.amount() * 100) / 100)
                .timestamp(LocalDateTime.now())
                .group(group)
                .owner(owner)
                .build();

        newExpense = expenseRepo.save(newExpense);
        return expenseMapper.toExpenseDto(newExpense);
    }

    public List<ExpenseDto> getGroupExpenses(String groupId) {
        String ownerId = UserUtil.getUserIdfromContext();
        Group group = groupRepo.getReferenceById(groupId);

        if (!hasAccessToGroup(ownerId, group)) {
            throw new AuthorizationDeniedException("You don't have access to this group");
        }

        List<Expense> expenses = expenseRepo.getAllByGroupOrderByTimestampDesc(group);
        return expenseMapper.toExpenseDtoSet(expenses);
    }
}
