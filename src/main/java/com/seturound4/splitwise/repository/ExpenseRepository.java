package com.seturound4.splitwise.repository;

import java.util.Optional;

import com.seturound4.splitwise.dao.Expense;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long>{

  Optional<Expense> findByExpenseId(Long expenseId);

}
