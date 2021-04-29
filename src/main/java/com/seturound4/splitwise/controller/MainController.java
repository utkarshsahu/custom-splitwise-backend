package com.seturound4.splitwise.controller;

import javax.validation.Valid;

import com.seturound4.splitwise.extras.AppAddExpenseRequest;
import com.seturound4.splitwise.extras.AppGroupAddRequest;
import com.seturound4.splitwise.extras.AppGroupRequest;
import com.seturound4.splitwise.extras.AppUserRequest;
import com.seturound4.splitwise.extras.ExpenseViewResponse;
import com.seturound4.splitwise.extras.UserViewResponse;
import com.seturound4.splitwise.service.SplitwiseService;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@RestController
public class MainController {
  private final SplitwiseService splitwiseService;

  public MainController(SplitwiseService splitwiseService) {
    this.splitwiseService = splitwiseService;
  }

  @PostMapping(value="/createUser")
  public ResponseEntity<String> createUser(@RequestBody @Valid AppUserRequest req) {
    return splitwiseService.createUser(req);
  }

  @PostMapping(value="/createUserGroup")
  public ResponseEntity<String> createUserGroup(@RequestBody @Valid AppGroupRequest req) {
    return splitwiseService.createUserGroup(req);
  }

  @PostMapping(value = "/addUsersToGroup")
  public ResponseEntity<String> addUsersToGroup(@RequestBody @Valid AppGroupAddRequest req) {
    return splitwiseService.addUsersToGroup(req);
  }

  @PostMapping(value = "/addExpense")
  public ResponseEntity<String> addExpense(@RequestBody @Valid AppAddExpenseRequest req) {
    return splitwiseService.addExpense(req);
  }

  // @ResponseBody
  @GetMapping(value = "/userView")
  public ResponseEntity<UserViewResponse> userView(@RequestParam String userPhoneNumber) {
    return splitwiseService.showUserView(userPhoneNumber);
  }

  @GetMapping(value = "/expenseView")
  public ResponseEntity<ExpenseViewResponse> expenseView(@RequestParam Long expenseId) {
    return splitwiseService.showExpenseView(expenseId);
  }
}
