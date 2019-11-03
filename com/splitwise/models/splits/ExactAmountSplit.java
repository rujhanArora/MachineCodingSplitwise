package com.splitwise.models.splits;

import com.splitwise.models.User;
import com.splitwise.models.expenses.ExpenseType;

public class ExactAmountSplit extends Split {
    private static ExpenseType type = ExpenseType.EXACT;

    public ExactAmountSplit(User user, double amount) {
        super(user);
        setAmount(amount);
    }
}
