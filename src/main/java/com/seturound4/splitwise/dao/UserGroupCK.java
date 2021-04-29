package com.seturound4.splitwise.dao;

import java.io.Serializable;

public class UserGroupCK implements Serializable{
  /**
   *
   */
  private static final long serialVersionUID = 4227388978372689604L;
  private Long groupId;
  private String phoneNumber;

  public UserGroupCK() {}

  public UserGroupCK(Long groupId, String phoneNumber) {
    this.groupId = groupId;
    this.phoneNumber = phoneNumber;
  }

  @Override
  public boolean equals(Object other) {
    UserGroupCK n = (UserGroupCK) other;
    return (this.groupId == n.groupId) && this.phoneNumber == n.phoneNumber;
  }

}
