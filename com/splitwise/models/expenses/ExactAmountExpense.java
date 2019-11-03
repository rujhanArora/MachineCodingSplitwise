package com.splitwise.models.expenses;

import com.splitwise.Utils;
import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.models.User;
import com.splitwise.models.splits.ExactAmountSplit;
import com.splitwise.models.splits.Split;

import java.util.List;

public class ExactAmountExpense extends Expense {
    private static ExpenseType type = ExpenseType.EXACT;

    public ExactAmountExpense(String name, double totalAmount, User createdBy) throws IllegalSplitException {
        super(name, totalAmount, createdBy);
    }

    @Override
    void validateSplits() throws IllegalSplitException {
        for (Split s : getSplits()) {
            if (!(s instanceof ExactAmountSplit)) {
                throw new IllegalSplitException("EqualExpense must have EqualSplit only");
            }
        }
    }

    @Override
    void recalculate() {
        List<Split> splits = getSplits();
        double sum = 0;
        for (Split s : splits) {
            sum += s.getAmount();
        }
        if (!Utils.isApproxEqual(sum, getTotalAmount()))
            splits.get(0).setAmount(splits.get(0).getAmount() + getTotalAmount() - sum);
        // things now add up correctly
    }

    @Override
    boolean validate() {
        // add extra logic here
        return super.validate();
    }
}
