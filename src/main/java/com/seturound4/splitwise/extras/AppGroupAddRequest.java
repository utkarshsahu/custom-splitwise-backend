package com.seturound4.splitwise.extras;

import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class AppGroupAddRequest {
  @NotNull
  private Long groupId;

  @NotEmpty
  private List<String> users;

  public AppGroupAddRequest(Long groupId, List<String> users) {
    this.groupId = groupId;
    this.users = users;
  }

  public Long getGroupId() {
    return this.groupId;
  }

  public List<String> getUsers() {
    return this.users;
  }
}
