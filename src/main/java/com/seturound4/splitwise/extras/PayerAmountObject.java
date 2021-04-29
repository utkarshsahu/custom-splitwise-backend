package com.seturound4.splitwise.extras;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class PayerAmountObject implements Comparable<PayerAmountObject>{
  @NotBlank
  private String phoneNumber;

  @DecimalMin(value = "0.0", inclusive = true)
  @NotNull
  private float amount;

  public String getPhoneNumber() {
    return phoneNumber;
  }
  public float getAmount() {
    return amount;
  }
  public void setAmount(float amount) {
    this.amount = amount;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public PayerAmountObject(String phoneNumber, float amount) {
    this.setAmount(amount);
    this.setPhoneNumber(phoneNumber);
  }

  @Override
  public int compareTo(PayerAmountObject o) {
    return Float.valueOf(this.getAmount()).compareTo(Float.valueOf(o.getAmount()));
  }
}
