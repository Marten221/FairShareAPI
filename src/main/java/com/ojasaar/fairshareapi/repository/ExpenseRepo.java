package com.ojasaar.fairshareapi.repository;

import com.ojasaar.fairshareapi.domain.model.Expense;
import com.ojasaar.fairshareapi.domain.model.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepo extends JpaRepository<Expense, String> {

    List<Expense> getAllByGroup(Group group);
}
