package com.seturound4.splitwise.extras;

import javax.validation.constraints.NotBlank;

public class AppUserRequest {

  @NotBlank
  private String name;

  @NotBlank
  private String phoneNumber;

  public AppUserRequest(String phoneNumber, String name) {
    this.phoneNumber = phoneNumber;
    this.name = name;
  }

  public String getName() {
    return this.name;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }
}
