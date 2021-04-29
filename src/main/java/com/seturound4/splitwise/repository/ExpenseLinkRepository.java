package com.seturound4.splitwise.repository;

import java.util.List;

import javax.transaction.Transactional;

import com.seturound4.splitwise.dao.ExpenseLink;
import com.seturound4.splitwise.dao.ExpenseLinkCK;
import com.seturound4.splitwise.extras.ExpenseLinkGrouped;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExpenseLinkRepository extends JpaRepository<ExpenseLink, ExpenseLinkCK>{

  @Transactional
  @Query(value = "insert into EXPENSE_LINK (expense_id, user_from_phone, user_to_phone, amount) values (:id, :from, :to, :amount) ", nativeQuery = true)
  @Modifying
  Object customSave(@Param("id") Long expenseId, @Param("from") String userFromPhone,
  @Param("to") String userToPhone, @Param("amount") float amount);

  @Query(value = "select e.user_from_phone AS userFromPhone, e.user_to_phone AS userToPhone, sum(amount) AS totalAmount from EXPENSE_LINK AS e where e.user_from_phone = :phone or e.user_to_phone = :phone group by e.user_from_phone, e.user_to_phone", nativeQuery = true)
  List<ExpenseLinkGrouped> findAllForPhoneNumber(@Param("phone") String phone);

  List<ExpenseLink> findAllByExpenseId(Long expenseId);


}
