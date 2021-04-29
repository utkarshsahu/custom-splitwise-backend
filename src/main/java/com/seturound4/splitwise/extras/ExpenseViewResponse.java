package com.seturound4.splitwise.extras;

import java.util.ArrayList;
import java.util.List;

public class ExpenseViewResponse {
  private Long expenseId;
  private String expenseName;

  private List<ExpenseDetail> expenseDetailList;

  public ExpenseViewResponse(Long expenseId, String expenseName, List<ExpenseDetail> expenseDetailList) {
    this.setExpenseId(expenseId);
    this.setExpenseName(expenseName);
    this.setExpenseDetail(expenseDetailList);
  }

  public List<ExpenseDetail> getExpenseDetailList() {
    return expenseDetailList;
  }

  public void setExpenseDetail(List<ExpenseDetail> expenseDetailList) {
    this.expenseDetailList = expenseDetailList;
  }

  public void addToExpenseDetailList(ExpenseDetail expenseDetail) {
    if (this.expenseDetailList == null) {
      this.expenseDetailList = new ArrayList<ExpenseDetail>();
    }

    this.expenseDetailList.add(expenseDetail);
  }

  public String getExpenseName() {
    return expenseName;
  }

  public void setExpenseName(String expenseName) {
    this.expenseName = expenseName;
  }

  public Long getExpenseId() {
    return expenseId;
  }

  public void setExpenseId(Long expenseId) {
    this.expenseId = expenseId;
  }

  public ExpenseViewResponse() {
  }
}
