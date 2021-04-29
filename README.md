## Steps to Run

1. Run `mvn clean compile` in root folder of this project

2. In application.properties, change spring.datasource.url to `/local/path/of/repo/test;DB_CLOSE_ON_EXIT=FALSE`

3. Run SplitwiseApplication.java in src/main/java/com/seturound4/splitwise

4. Navigate to localhost:8080/swagger-ui.html

## APIs

1. /createUser (POST) -> name: string, phoneNumber: string (Key) -> Creates a user by making an entry to User table with phoneNumber as identifying column
2. /createUserGroup (POST) -> groupName: string, users: List of users' phoneNumbers -> Creates a splitwise group composed of more than one users. Throws validation errors if groupSize is less than 2. Returns BadRequest if any specified phoneNumber in users list doesn't occur in the User table.
3. /addUsersToGroup (POST) -> groupId : string, users: List of users' phoneNumbers -> Adds more users to an existing group. Returns BadRequest if the groupId doesn't exist. Returns BadRequest if any specified phoneNumber in users list doesn't occur in the User table.
4. /addExpense (POST) -> Adds an expense based on multiple parameters as described below
splitType can take four possible values -> equal, unequal, shares, percentage
If splitType is equal, then paymentTo must be properly defined
If splitType is unequal, then customSplit must be properly defined
If splitType is shares, then shareSplit must be properly defined
Example request:
```
{
  "customSplit": [
    {
      "amount": 140,
      "phoneNumber": "9112"
    },
    {
      "amount": 160,
      "phoneNumber": "4553"
    }
  ],
  "expenseName": "Friday Shindig",
  "paymentFrom": [
    {
      "amount": 100,
      "phoneNumber": "1234"
    },
    {
      "amount": 200,
      "phoneNumber": "3456"
    }
  ],
  "paymentTo": [
    "9112", "4553"
  ],
  "shareSplit": [
    {
      "phoneNumber": "9112",
      "shares": 3
    },
    {
      "phoneNumber": "4553",
      "shares": 4
    }
  ],
  "splitType": "equal",
  "totalAmount": 300
}
```
5. /userView (GET) -> userPhoneNumber: string -> For a user, this view shows all the obligations/payments status that he/she has with other users

6. /expenseView (GET) -> expenseId: string -> For an expense, this view shows all the users involved with the expense and how much have they paid and owe in this particular expense.