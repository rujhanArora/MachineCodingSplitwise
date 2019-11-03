package com.splitwise.models.expenses;

import com.splitwise.Utils;
import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.models.User;
import com.splitwise.models.splits.PercentSplit;
import com.splitwise.models.splits.Split;

import java.util.List;

public class PercentExpense extends Expense {
    private static ExpenseType type = ExpenseType.PERCENT;

    public PercentExpense(String name, double totalAmount, User createdBy) throws IllegalSplitException {
        super(name, totalAmount, createdBy);
    }

    @Override
    void validateSplits() throws IllegalSplitException {
        for(Split s: getSplits()) {
            if (!(s instanceof PercentSplit)) {
                throw new IllegalSplitException("EqualExpense must have EqualSplit only");
            }
        }
    }

    @Override
    void recalculate() {
        double sum = 0;
        double amount;
        List<Split> splits = getSplits();
        for(Split s: splits) { // runtime polymorphism
            PercentSplit ps = (PercentSplit) s; // casting explicitly to use exact implementation
            amount = Utils.roundOff(getTotalAmount() * ps.getPercent() / 100.0d);
            s.setAmount(amount);
            sum += s.getAmount();
        }

        if(!Utils.isApproxEqual(sum, getTotalAmount()))
            splits.get(0).setAmount(splits.get(0).getAmount() + getTotalAmount() - sum);
        // things now add up to 100
    }

    @Override
    boolean validate() {
        return super.validate();
        // add extra logic here
    }
}
