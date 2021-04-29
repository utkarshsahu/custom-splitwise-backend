package com.seturound4.splitwise.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;


@Entity
public class Expense {
  @Id
  @GeneratedValue
  @Column(name = "expense_id")
  private Long expenseId;

  private String expenseName;

  private float totalAmount;

  @Column(name = "group_id", insertable = false, updatable = false)
  private Long groupId;

  @OneToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "group_id", referencedColumnName = "group_id")
  private GroupInfo group;

  public String getExpenseName() {
    return expenseName;
  }

  public float getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(float totalAmount) {
    this.totalAmount = totalAmount;
  }

  public void setExpenseName(String expenseName) {
    this.expenseName = expenseName;
  }

  public Long getExpenseId() {
    return expenseId;
  }

  public Expense(String expenseName, float totalAmount, Long groupId) {
    this.setExpenseName(expenseName);
    this.setTotalAmount(totalAmount);
    this.groupId = groupId;
  }

  public Expense() {
  }

}
