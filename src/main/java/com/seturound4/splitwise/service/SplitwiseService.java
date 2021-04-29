package com.seturound4.splitwise.service;

import com.seturound4.splitwise.dao.User;
import com.seturound4.splitwise.dao.UserGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import com.seturound4.splitwise.dao.Expense;
import com.seturound4.splitwise.dao.ExpenseLink;
import com.seturound4.splitwise.dao.GroupInfo;
import com.seturound4.splitwise.extras.AppAddExpenseRequest;
import com.seturound4.splitwise.extras.AppGroupAddRequest;
import com.seturound4.splitwise.extras.AppGroupRequest;
import com.seturound4.splitwise.extras.AppUserRequest;
import com.seturound4.splitwise.extras.ExpenseDetail;
import com.seturound4.splitwise.extras.ExpenseLinkGrouped;
import com.seturound4.splitwise.extras.ExpenseViewResponse;
import com.seturound4.splitwise.extras.PayerAmountObject;
import com.seturound4.splitwise.extras.ShareSplitObject;
import com.seturound4.splitwise.extras.UserObligation;
import com.seturound4.splitwise.extras.UserViewResponse;
import com.seturound4.splitwise.repository.UserGroupRepository;
import com.seturound4.splitwise.repository.ExpenseLinkRepository;
import com.seturound4.splitwise.repository.ExpenseRepository;
import com.seturound4.splitwise.repository.GroupRepository;
import com.seturound4.splitwise.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class SplitwiseService {

  private final UserRepository userRepo;
  private final UserGroupRepository userGroupRepo;
  private final GroupRepository groupRepo;
  private final ExpenseRepository expenseRepo;
  private final ExpenseLinkRepository expenseLinkRepo;

  Logger logger = LoggerFactory.getLogger(SplitwiseService.class);

  public SplitwiseService (UserRepository userRepo, UserGroupRepository userGroupRepo, GroupRepository groupRepo, ExpenseRepository expenseRepo, ExpenseLinkRepository expenseLinkRepo) {
    this.userRepo = userRepo;
    this.userGroupRepo = userGroupRepo;
    this.groupRepo = groupRepo;
    this.expenseRepo = expenseRepo;
    this.expenseLinkRepo = expenseLinkRepo;
  }

  public ResponseEntity<String> createUser(AppUserRequest req) {
    User u = new User(req.getPhoneNumber(), req.getName());
    if (userRepo.findById(req.getPhoneNumber()).isPresent()) {
      return new ResponseEntity<>("User already created!", HttpStatus.OK);
    } else {
      userRepo.save(u);
      return new ResponseEntity<>("Created user", HttpStatus.OK);
    }
  }

  public ResponseEntity<String> createUserGroup(AppGroupRequest req) {
    String groupName = req.getGroupName();

    for (String phoneNumber : req.getUsers()) {
      if (!userRepo.findById(phoneNumber).isPresent()) {
        return new ResponseEntity<>("No user exists with phone number - " + phoneNumber, HttpStatus.OK);
      }
    }

    // todo: Check if a group already exists with following users
    GroupInfo g = groupRepo.save(new GroupInfo(groupName));

    for (String phoneNumber: req.getUsers()) {
      UserGroup ug = new UserGroup(g.getGroupId(), phoneNumber);
      userGroupRepo.save(ug);
    }
    return new ResponseEntity<>("Created a user group", HttpStatus.OK);
  }

  public ResponseEntity<String> addUsersToGroup(AppGroupAddRequest req) {

    if (groupRepo.findById(req.getGroupId()).isPresent()) {
      for (String phoneNumber: req.getUsers()) {
        UserGroup ug = new UserGroup(req.getGroupId(), phoneNumber);
        userGroupRepo.save(ug);
      }
      return new ResponseEntity<>("Users added to group", HttpStatus.OK);
    } else {
      return new ResponseEntity<>("No group exists with group ID -" + req.getGroupId().toString(), HttpStatus.OK);
    }
  }

  public ResponseEntity<String> addExpense(AppAddExpenseRequest req) {
    List<PayerAmountObject> paymentFrom = req.getPaymentFrom();
    HashMap<String,Boolean> map = new HashMap<String,Boolean>();
    float from_sum = 0;
    for (PayerAmountObject obj : paymentFrom) {
      float amount = obj.getAmount();
      from_sum += amount;
      // if (amount > 0.0) {
      //   map.put(obj.getPhoneNumber(), amount);
      // }
    }

    if (from_sum != req.getTotalAmount()) {
      return new ResponseEntity<>("Amount paid by individual users doesn't match total amount specified", HttpStatus.BAD_REQUEST);
    }

    String splitType = req.getSplitType();
    List<String> paymentToPhone = req.getPaymentTo();
    List<PayerAmountObject> paymentTo = new ArrayList<PayerAmountObject>();

    if (splitType.equals("equal")) {
      float equalAmount = customRound(req.getTotalAmount() / (float) paymentToPhone.size());
      for (String payeePhone : req.getPaymentTo()) {
        if (!userRepo.findById(payeePhone).isPresent()) {
          return new ResponseEntity<>("No payee exists with phone number " + payeePhone, HttpStatus.BAD_REQUEST);
        }
        paymentTo.add(new PayerAmountObject(payeePhone, equalAmount));
      }
    } else if (splitType.equals("unequal")) {

      if (req.getCustomSplit() == null || req.getCustomSplit().size() < paymentToPhone.size()) {
        return new ResponseEntity<>("customSplit parameter badly defined!", HttpStatus.BAD_REQUEST);
      }
      float to_sum = 0;
      for (PayerAmountObject obj : req.getCustomSplit()) {
        if (!userRepo.findById(obj.getPhoneNumber()).isPresent()) {
          return new ResponseEntity<>("No payee exists with phone number " + obj.getPhoneNumber(), HttpStatus.BAD_REQUEST);
        }
        to_sum += obj.getAmount();
        if (obj.getAmount() > 0.0) {
          paymentTo.add(new PayerAmountObject(obj.getPhoneNumber(), obj.getAmount()));
        }
      }

      if (req.getTotalAmount() != to_sum) {
        return new ResponseEntity<>("customSplit parameter badly defined!", HttpStatus.BAD_REQUEST);
      }


    } else if (splitType.equals("shares")) {
      Long tot_shares = 0L;
      for (ShareSplitObject obj : req.getShareSplit()) {
        if (!userRepo.findById(obj.getPhoneNumber()).isPresent()) {
          return new ResponseEntity<>("No payee exists with phone number " + obj.getPhoneNumber(), HttpStatus.BAD_REQUEST);
        }
        tot_shares += obj.getShares();
      }
      if (tot_shares == 0L) {
        return new ResponseEntity<>("total shares should be greater than zero!", HttpStatus.BAD_REQUEST);
      }

      for (ShareSplitObject obj : req.getShareSplit()) {
        if (obj.getShares() > 0) {
          float amount = customRound(((float) obj.getShares() / (float) tot_shares)*req.getTotalAmount());
          paymentTo.add(new PayerAmountObject(obj.getPhoneNumber(), amount));
        }
      }
    } else if (splitType.equals("percentage")) {
      return new ResponseEntity<String> ("Percentage split feature not implemented yet", HttpStatus.NOT_IMPLEMENTED);
    }
    // List<ExpenseLink> expenseRecords = new ArrayList<ExpenseLink>();
    Expense ex = expenseRepo.save(new Expense(req.getExpenseName(), req.getTotalAmount(), null));

    Collections.sort(paymentFrom, Collections.reverseOrder());
    Collections.sort(paymentTo, Collections.reverseOrder());

    for (PayerAmountObject fr: paymentFrom) {
      float payerAmount = fr.getAmount();
      if (payerAmount > 0.0) {
        for (PayerAmountObject to: paymentTo) {
          if (map.containsKey(to.getPhoneNumber())) {
            continue;
          }

          float payeeAmount = to.getAmount();
          if (payerAmount >= payeeAmount) {
            payerAmount -= payeeAmount;
            expenseLinkRepo.customSave(ex.getExpenseId(), fr.getPhoneNumber(), to.getPhoneNumber(), payeeAmount);
            map.put(to.getPhoneNumber(), true);
            if (payerAmount == 0.0) {
              break;
            }
          } else {
            expenseLinkRepo.customSave(ex.getExpenseId(), fr.getPhoneNumber(), to.getPhoneNumber(), payerAmount);
            to.setAmount(payeeAmount - payerAmount);
            break;
          }
        }
      }
    }

    return new ResponseEntity<>("Expense added! ID - " + ex.getExpenseId().toString(), HttpStatus.OK);
  }


  private float customRound(float val) {
    return (float) (Math.round(val * 100.0) / 100.0);
  }

  public ResponseEntity<UserViewResponse> showUserView(String userPhoneNumber) {
    Optional<User> uo = userRepo.findByPhoneNumber(userPhoneNumber);

    if (!uo.isPresent()) {
      return new ResponseEntity<UserViewResponse> (new UserViewResponse(), HttpStatus.BAD_REQUEST);
    }

    List<ExpenseLinkGrouped> allList = expenseLinkRepo.findAllForPhoneNumber(userPhoneNumber);
    HashMap<String, Float> map = new HashMap<String, Float>();

    for (ExpenseLinkGrouped l : allList) {
      String fromPhone = l.getUserFromPhone();
      String toPhone = l.getUserToPhone();

      logger.info(fromPhone + " " + toPhone + " " + l.getTotalAmount());
      if (fromPhone.equals(userPhoneNumber)) {
        if (!toPhone.equals(userPhoneNumber)) {
          map.put(toPhone, map.getOrDefault(toPhone, (float) 0.0) + l.getTotalAmount());
        }
      } else {
        map.put(fromPhone, map.getOrDefault(fromPhone, (float) 0.0) - l.getTotalAmount());
      }
    }
    UserViewResponse resp = new UserViewResponse();
    resp.setName(uo.get().getName());
    resp.setPhoneNumber(userPhoneNumber);

    for (Map.Entry mapElement : map.entrySet()) {
      String key = (String) mapElement.getKey();
      float value = (float) mapElement.getValue();
      UserObligation u = new UserObligation(key, value > 0 ? value : (0-value), (value > 0) ? "gets back" : (value == 0) ? "settled" : "owes");
      resp.addObligations(u);
    }
    return new ResponseEntity<UserViewResponse> (resp, HttpStatus.OK);
  }

  public ResponseEntity<ExpenseViewResponse> showExpenseView(Long expenseId) {
    Optional<Expense> eo = expenseRepo.findByExpenseId(expenseId);

    if (!eo.isPresent()) {
      return new ResponseEntity<ExpenseViewResponse> (new ExpenseViewResponse(), HttpStatus.BAD_REQUEST);
    }
    HashMap<String, Float> paidMap = new HashMap<String, Float>();
    HashMap<String, Float> owesMap = new HashMap<String, Float>();
    Set<String> allUsers = new HashSet<String>();

    List<ExpenseLink> el = expenseLinkRepo.findAllByExpenseId(expenseId);

    for (ExpenseLink e : el) {
      String fromPhone = e.getUserFromPhone();
      String toPhone = e.getUserToPhone();
      allUsers.add(fromPhone);
      allUsers.add(toPhone);

      paidMap.put(fromPhone, paidMap.getOrDefault(fromPhone, (float) 0.0) + e.getAmount());
      owesMap.put(toPhone, owesMap.getOrDefault(toPhone, (float) 0.0) + e.getAmount());
    }

    ExpenseViewResponse resp = new ExpenseViewResponse();
    resp.setExpenseId(expenseId);
    resp.setExpenseName(eo.get().getExpenseName());

    for (String userPhone : allUsers) {
      String userName = userRepo.findById(userPhone).get().getName();
      ExpenseDetail ed = new ExpenseDetail(userName, paidMap.getOrDefault(userPhone, (float) 0.0),
      owesMap.getOrDefault(userPhone, (float) 0.0));
      resp.addToExpenseDetailList(ed);
    }

    return new ResponseEntity<>(resp, HttpStatus.OK);
  }

}

