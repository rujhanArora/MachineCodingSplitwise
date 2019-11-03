package com.splitwise.commands;

import com.splitwise.Utils;
import com.splitwise.exceptions.BadCommandFormatException;
import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.exceptions.NoSuchUserException;
import com.splitwise.models.User;
import com.splitwise.models.exceptions.InvalidExpenseTypeException;
import com.splitwise.models.expenses.Expense;
import com.splitwise.models.expenses.ExpenseFactory;
import com.splitwise.models.expenses.ExpenseType;
import com.splitwise.models.splits.Split;
import com.splitwise.models.splits.SplitFactory;

import java.util.ArrayList;
import java.util.List;

public class AddExpenseCommand implements Command {

    @Override
    public void execute(String[] cmd) throws BadCommandFormatException {
        ExpenseType expType;
        try {
            expType  = ExpenseType.fromString(cmd[1]);
        } catch (InvalidExpenseTypeException e) {
            System.out.println(e.getMessage());
            return;
        }
        String name = cmd[2];
        double totalAmount = Double.parseDouble(cmd[3]);
        User createdBy;
        try {
            createdBy = Utils.getUser(cmd[4]);
        } catch (NoSuchUserException e) {
            System.out.println(e.getMessage());
            return;
        }
        Expense expense;
        try {
            expense = ExpenseFactory.createExpense(expType, name, createdBy, totalAmount);
        } catch (InvalidExpenseTypeException | IllegalSplitException ignore) {
            return;
        }

        if(cmd.length > 5) {
            User paidBy;
            try {
                paidBy = Utils.getUser(cmd[4]);
            } catch (NoSuchUserException e) {
                System.out.println(e.getMessage());
                return;
            }
            expense.setPaidBy(paidBy);

            int numberOfSplits = cmd.length - 5;
            if(numberOfSplits % 2 != 0) {
                System.out.println("Invalid format!\nExpected: > add_expense expense_type name created_by [paid_by] [user val user val user val]");
            }
            List<Split> splits = new ArrayList<>();
            if(expType.equals(ExpenseType.EQUAL)) {
                for(int i = 0; i < numberOfSplits; i ++) {
                    User user;
                    try {
                        user = Utils.getUser(cmd[5+i]);
                    } catch (NoSuchUserException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                    Split split = null;
                    try {
                        split = SplitFactory.createSplit(expType, user);
                    } catch (InvalidExpenseTypeException e) {
                        e.printStackTrace();
                    }
                    splits.add(split);
                }
            } else {
                for(int i = 0; i < numberOfSplits; i += 2) {
                    User user;
                    try {
                        user = Utils.getUser(cmd[5+i]);
                    } catch (NoSuchUserException e) {
                        System.out.println(e.getMessage());
                        return;
                    }

                    double amountOrPercent = Double.parseDouble(cmd[5+i+1]);
                    Split split = null;
                    try {
                        split = SplitFactory.createSplit(expType, user, amountOrPercent);
                    } catch (InvalidExpenseTypeException e) {
                        System.out.println(e.getMessage());
                        return;
                    }
                    splits.add(split);
                }
            }
            try {
                expense.setSplits(splits);
            } catch (IllegalSplitException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }
}
