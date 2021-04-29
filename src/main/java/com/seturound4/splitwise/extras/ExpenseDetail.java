package com.seturound4.splitwise.extras;

public class ExpenseDetail {
  private String name;
  private float paid;
  private float owes;

  public ExpenseDetail(String name, float paid, float owes) {
    this.setName(name);
    this.setPaid(paid);
    this.setOwes(owes);
  }
  public float getOwes() {
    return owes;
  }
  public void setOwes(float owes) {
    this.owes = owes;
  }
  public float getPaid() {
    return paid;
  }
  public void setPaid(float paid) {
    this.paid = paid;
  }
  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }
  public ExpenseDetail() {
  }
}
