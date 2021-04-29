package com.seturound4.splitwise.repository;

import java.util.Optional;

import com.seturound4.splitwise.dao.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{

  Optional<User> findByPhoneNumber(String phoneNumber);
}
