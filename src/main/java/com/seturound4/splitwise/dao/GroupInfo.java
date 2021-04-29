package com.seturound4.splitwise.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class GroupInfo {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "group_id")
  private Long groupId;

  private String groupName;

  public GroupInfo() {}

  public GroupInfo(String groupName) {
    this.groupName = groupName;
  }

  public String getGroupName(){
    return this.groupName;
  }

  public Long getGroupId() {
    return this.groupId;
  }
}
