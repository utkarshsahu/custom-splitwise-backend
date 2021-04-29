package com.seturound4.splitwise.repository;

import java.util.List;

import com.seturound4.splitwise.dao.UserGroup;
import com.seturound4.splitwise.dao.UserGroupCK;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserGroupRepository extends CrudRepository<UserGroup, UserGroupCK> {

  @Query(value = "select phone_number from UserGroup u where u.groupId = :groupId", nativeQuery = true)
  List<String> findPhoneNumberByGroupId(Long groupId);
}
