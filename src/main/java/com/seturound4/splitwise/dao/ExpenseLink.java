package com.seturound4.splitwise.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(ExpenseLinkCK.class)
public class ExpenseLink {

  @Id
  @Column(name = "expense_id", insertable = false, updatable = false)
  private Long expenseId;

  @Id
  @Column(name = "user_from_phone", insertable = false, updatable = false)
  private String userFromPhone;

  @Id
  @Column(name = "user_to_phone", insertable = false, updatable = false)
  private String userToPhone;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "expense_id", referencedColumnName = "expense_id")
  private Expense expense;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_from_phone", referencedColumnName = "phone_number")
  private User userFrom;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_to_phone", referencedColumnName = "phone_number")
  private User userTo;

  private String expenseType;

  @Column(precision = 10, scale = 2)
  private float amount;


  public ExpenseLink(Long expenseId, String userFromPhone, String userToPhone, float amount, String expenseType) {
    this.expenseId = expenseId;
    this.userFromPhone = userFromPhone;
    this.userToPhone = userToPhone;
    this.amount = amount;
    this.expenseType = expenseType;
  }

  public ExpenseLink() {}

  public String getExpenseType() {
    return expenseType;
  }

  public float getAmount() {
    return amount;
  }

  public void setAmount(float amount) {
    this.amount = amount;
  }

  public void setExpenseType(String expenseType) {
    this.expenseType = expenseType;
  }

  public Long getExpenseId() {
    return expenseId;
  }

  public void setExpenseId(Long expenseId) {
    this.expenseId = expenseId;
  }

  public String getUserFromPhone() {
    return userFromPhone;
  }

  public void setUserFromPhone(String userFromPhone) {
    this.userFromPhone = userFromPhone;
  }

  public String getUserToPhone() {
    return userToPhone;
  }

  public void setUserToPhone(String userToPhone) {
    this.userToPhone = userToPhone;
  }

}
