package com.splitwise.models.splits;

import com.splitwise.models.User;
import com.splitwise.models.expenses.ExpenseType;

public class PercentSplit extends Split {
    private double percent;
    private static ExpenseType type = ExpenseType.PERCENT;


    public PercentSplit(User user, double percent) {
        super(user);
        setPercent(percent);
    }

    public double getPercent() {
        return percent;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }
}
