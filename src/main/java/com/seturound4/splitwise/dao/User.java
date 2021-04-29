package com.seturound4.splitwise.dao;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User {
  @Id
  @Column(name = "phone_number")
  String phoneNumber;
  String name;

  public User() {}

  public User(String phoneNumber, String name) {
    this.phoneNumber = phoneNumber;
    this.name = name;
  }

  public String getPhoneNumber() {
    return this.phoneNumber;
  }

  public String getName() {
    return this.name;
  }
}