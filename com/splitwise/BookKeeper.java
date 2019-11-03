package com.splitwise;

import com.splitwise.exceptions.EmailAlreadyUsedException;
import com.splitwise.exceptions.NoSuchUserException;
import com.splitwise.models.User;
import com.splitwise.models.expenses.Expense;
import com.splitwise.models.expenses.ExpenseFactory;
import com.splitwise.models.expenses.ExpenseType;
import com.splitwise.models.splits.Split;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

// tomorrow
// how we we pull up all those methods from expense into the bookkeeper
// how to simply expenses
// Command Line Interface <- simple

public class BookKeeper { // Controller + DB
    private Map<Long, Expense> expenses;
    private Map<Long, User> users;
    private Map<String, User> userByEmail;
    private Map<User, Map<User, Double>> balances; // user -> user -> amount


    private static BookKeeper INSTANCE;

    private BookKeeper() {
        expenses = new HashMap<>();
        users = new HashMap<>();
        balances = new HashMap<>();
        userByEmail = new HashMap<>();
    }

    public static BookKeeper getInstance() {
        // singleton pattern
        if (INSTANCE == null)
            INSTANCE = new BookKeeper();
        return INSTANCE;
    }

    public void addUser(User user) {
        users.put(user.getUid(), user);
        if (user.getEmail() != null || user.getEmail() != "")
            userByEmail.put(user.getEmail(), user);
        balances.put(user, new HashMap<>());
        System.out.println(user.toString() + " added successfully!");
    }

    // two tasks
    // on creating expense - done
    // on updating expense

    public void AddExpense(String name,
                           ExpenseType type,
                           User createdBy,
                           User paidBy,
                           List<Split> splits,
                           double totalAmount) throws Exception {
        Expense e = ExpenseFactory.createExpense(type, name, createdBy, totalAmount);
        expenses.put(e.getUid(), e);
        createdBy.getExpenses().add(e);
        e.setPaidBy(paidBy);
        e.setSplits(splits);

        if(!balances.containsKey(paidBy))
            throw new NoSuchUserException("Please add the user before adding their expenses");
        Map<User, Double> userBalances;

        for(Split s: splits) {
            User paidTo = s.getUser();
            if(paidBy.equals(paidTo)) continue;

            userBalances = balances.get(paidBy);
            userBalances.put(paidTo, s.getAmount() + userBalances.getOrDefault(paidTo, 0.0d));

            if(!balances.containsKey(paidTo))
                throw new NoSuchUserException("Please add the user before adding their expenses");
            userBalances = balances.get(paidTo);
            userBalances.put(paidBy, s.getAmount() + userBalances.getOrDefault(paidBy, 0.0d));
        }
    }

    public void showAllBalances() {
        for(User user1: balances.keySet()) {
            showBalance(user1);
        }
    }

    public void showAllBalances(boolean simplify) {
        if(!simplify)
            showAllBalances();

        // todo: implement this
    }

    public void showBalance(User user) {
        Map<User, Double> userBalances = balances.get(user);
        boolean hadBalance = false;
        for(User user2: userBalances.keySet()) {
            double amount = userBalances.get(user2);
            // print user1 owes amount to user 2
            if(amount > 0) {
                System.out.println(user.getName() + " owes " + amount + " to " + user2.getName());
                hadBalance = true;
            }
            else if(amount < 0) {
                System.out.println(user.getName() + " gets " + (-amount) + " from " + user2.getName());
                hadBalance = true;
            }
            // if amount is 0
        }
        if(!hadBalance) {
            System.out.println(user.getName() + " has no dues!"); // think of this preemptively is difficult!
        }
    }

    public void showBalance(Long userId) throws NoSuchUserException {
        showBalance(getUser(userId));
    }

    public User getUser(Long uid) throws NoSuchUserException {
        if (!users.containsKey(uid))
            throw new NoSuchUserException("User with uid=" + uid + " doesn't exist!");
        return users.get(uid);
        // a lot of points in the actual machine coding round
    }

    public User getUser(String email) throws NoSuchUserException {
        for (User u : users.values())
            if (u.getEmail().equals(email))
                return u;
        throw new NoSuchUserException("User with email=" + email + " doesn't exist!");
    }

    public void changeEmail(User user, String email) throws EmailAlreadyUsedException {
        if (userByEmail.containsKey(email)) {
            if (!userByEmail.get(email).equals(user)) {
                throw new EmailAlreadyUsedException();
            }
        }
        if (userByEmail.containsKey(user.getEmail())) {
            userByEmail.remove(user.getEmail());
        }
        user.setEmail(email);
        userByEmail.put(email, user);
    }
}
