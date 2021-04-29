package com.seturound4.splitwise.dao;

import java.io.Serializable;

public class ExpenseLinkCK implements Serializable{
  /**
   *
   */
  private static final long serialVersionUID = 1L;
  private Long expenseId;
  private String userFromPhone;
  private String userToPhone;
  public Long getExpenseId() {
    return expenseId;
  }
  public String getUserToPhone() {
    return userToPhone;
  }
  public void setUserToPhone(String userToPhone) {
    this.userToPhone = userToPhone;
  }
  public String getUserFromPhone() {
    return userFromPhone;
  }
  public void setUserFromPhone(String userFromPhone) {
    this.userFromPhone = userFromPhone;
  }
  public void setExpenseId(Long expenseId) {
    this.expenseId = expenseId;
  }
  public ExpenseLinkCK(Long expenseId, String userFromPhone, String userToPhone) {
    this.expenseId = expenseId;
    this.userFromPhone = userFromPhone;
    this.setUserToPhone(userToPhone);
  }
  public ExpenseLinkCK() {
  }


}
