package com.seturound4.splitwise.extras;

import java.util.ArrayList;
import java.util.List;

public class UserViewResponse {
  private String phoneNumber;
  private String name;

  private List<UserObligation> obligations;

  public UserViewResponse(String phoneNumber, String name, List<UserObligation> payments) {
    this.setPhoneNumber(phoneNumber);
    this.setName(name);
    this.setObligations(payments);
  }

  public UserViewResponse(){}

  public List<UserObligation> getPayments() {
    return obligations;
  }

  public void setObligations(List<UserObligation> obligations) {
    this.obligations = obligations;
  }

  public void addObligations(UserObligation obligation) {
    if (this.obligations == null) {
      this.obligations = new ArrayList<UserObligation>();
    }
    this.obligations.add(obligation);
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }
}
