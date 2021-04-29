package com.seturound4.splitwise.extras;

public class UserObligation {
  private String phoneNumber;
  private float amount;
  private String status;
  public String getPhoneNumber() {
    return phoneNumber;
  }
  public float getAmount() {
    return amount;
  }
  public void setAmount(float amount) {
    this.amount = amount;
  }
  public String getStatus() {
    return status;
  }
  public void setStatus(String status) {
    this.status = status;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  public UserObligation(String phoneNumber, float amount, String status) {
    this.phoneNumber = phoneNumber;
    this.setAmount(amount);
    this.setStatus(status);
  }
  public UserObligation() {
  }
}
