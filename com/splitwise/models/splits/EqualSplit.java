package com.splitwise.models.splits;

import com.splitwise.models.User;
import com.splitwise.models.expenses.ExpenseType;

public class EqualSplit extends Split {
    private static ExpenseType type = ExpenseType.EQUAL;

    public EqualSplit(User user) {
        super(user);
    }
}
