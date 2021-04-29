package com.seturound4.splitwise.dao;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;


@Entity
@IdClass(UserGroupCK.class)
public class UserGroup {

  @Id
  private Long groupId;

  @Id
  private String phoneNumber;

  public UserGroup() {}

  public UserGroup(Long groupId, String phoneNumber) {
    this.groupId = groupId;
    this.phoneNumber = phoneNumber;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public Long getGroupId() {
    return this.groupId;
  }
}
