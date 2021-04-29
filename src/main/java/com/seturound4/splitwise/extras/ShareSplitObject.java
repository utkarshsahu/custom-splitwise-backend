package com.seturound4.splitwise.extras;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class ShareSplitObject {
  @NotBlank
  private String phoneNumber;

  @Min(value = 0)
  @NotNull
  private Long shares;

  public String getPhoneNumber() {
    return phoneNumber;
  }
  public Long getShares() {
    return shares;
  }
  public void setShares(Long shares) {
    this.shares = shares;
  }
  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
  public ShareSplitObject(String phoneNumber, Long shares) {
    this.setPhoneNumber(phoneNumber);
    this.setShares(shares);
  }
}
