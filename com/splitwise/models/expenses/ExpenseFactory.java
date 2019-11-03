package com.splitwise.models.expenses;

import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.models.User;
import com.splitwise.models.exceptions.InvalidExpenseTypeException;

public class ExpenseFactory {
    public static Expense createExpense(ExpenseType type,
                                        String name,
                                        User createdBy,
                                        double totalAmount) throws InvalidExpenseTypeException, IllegalSplitException {
        switch (type) {
            case EXACT:
                return new ExactAmountExpense(name, totalAmount, createdBy);
            case PERCENT:
                return new PercentExpense(name, totalAmount, createdBy);
            case EQUAL:
                return new EqualExpense(name, totalAmount, createdBy);
            default:
                throw new InvalidExpenseTypeException("Invalid Expense Type");
        }
    }
}
