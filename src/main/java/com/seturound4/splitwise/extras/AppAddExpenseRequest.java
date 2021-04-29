package com.seturound4.splitwise.extras;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Pattern;

public class AppAddExpenseRequest {
  @NotBlank
  private String expenseName;

  @NotEmpty
  private List<PayerAmountObject> paymentFrom;

  @NotEmpty
  private List<String> paymentTo;

  @NotNull
  @Positive
  private float totalAmount;

  @NotBlank
  @Pattern(regexp = "equal|unequal|shares|percentage")
  private String splitType;

  @Valid
  private List<ShareSplitObject> shareSplit;

  @Valid
  private List<PayerAmountObject> customSplit;

  public AppAddExpenseRequest(List<PayerAmountObject> paymentFrom, List<String> paymentTo, Long totalAmount,
      String splitType, List<ShareSplitObject> shareSplit, List<PayerAmountObject> customSplit) {
    this.setPaymentFrom(paymentFrom);
    this.setPaymentTo(paymentTo);
    this.setTotalAmount(totalAmount);
    this.setSplitType(splitType);
    this.setShareSplit(shareSplit);
    this.setCustomSplit(customSplit);
  }

  public List<PayerAmountObject> getCustomSplit() {
    return customSplit;
  }

  public void setCustomSplit(List<PayerAmountObject> customSplit) {
    this.customSplit = customSplit;
  }

  public List<ShareSplitObject> getShareSplit() {
    return shareSplit;
  }

  public void setShareSplit(List<ShareSplitObject> splitStock) {
    this.shareSplit = splitStock;
  }

  public String getSplitType() {
    return splitType;
  }

  public void setSplitType(String splitType) {
    this.splitType = splitType;
  }

  public float getTotalAmount() {
    return totalAmount;
  }

  public void setTotalAmount(Long totalAmount) {
    this.totalAmount = totalAmount;
  }

  public List<PayerAmountObject> getPaymentFrom() {
    return paymentFrom;
  }
  public List<String> getPaymentTo() {
    return paymentTo;
  }
  public void setPaymentTo(List<String> paymentTo) {
    this.paymentTo = paymentTo;
  }
  public void setPaymentFrom(List<PayerAmountObject> paymentFrom) {
    this.paymentFrom = paymentFrom;
  }

  public String getExpenseName() {
    return expenseName;
  }

  public void setExpenseName(String expenseName) {
    this.expenseName = expenseName;
  }

}
