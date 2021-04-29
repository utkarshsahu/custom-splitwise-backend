package com.seturound4.splitwise.extras;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class AppGroupRequest {
  @NotBlank
  private String groupName;

  @NotEmpty
  private List<String> users;

  public AppGroupRequest(String groupName, List<String> users) {
    this.groupName = groupName;
    this.users = users;
  }

  public String getGroupName() {
    return this.groupName;
  }

  public List<String> getUsers() {
    return this.users;
  }
}
