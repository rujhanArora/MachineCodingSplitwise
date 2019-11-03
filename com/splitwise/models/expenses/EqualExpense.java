package com.splitwise.models.expenses;
import com.splitwise.Utils;
import com.splitwise.exceptions.IllegalSplitException;
import com.splitwise.models.User;
import com.splitwise.models.splits.EqualSplit;
import com.splitwise.models.splits.Split;
import java.util.List;

public class EqualExpense extends Expense {
    private static ExpenseType type = ExpenseType.EQUAL;

    public EqualExpense(String name, double totalAmount, User createdBy) throws IllegalSplitException {
        // make sure that we call super
        super(name, totalAmount, createdBy);
        // cannot calculate amounts here
    }

    @Override
    void validateSplits() throws IllegalSplitException {
        for(Split s: getSplits()) {
            if (!(s instanceof EqualSplit)) {
                throw new IllegalSplitException("EqualExpense must have EqualSplit only");
            }
        }
    }

    @Override
    void recalculate() {
        List<Split> splits = getSplits();
        double sum = 0;
        double amount;
        int numUsers = getSplits().size();
        for(Split s: splits) {
            amount = Utils.roundOff(getTotalAmount() / numUsers);
            s.setAmount(amount);
            sum += s.getAmount();
        }

        if(!Utils.isApproxEqual(sum, getTotalAmount()))
            splits.get(0).setAmount(splits.get(0).getAmount() + getTotalAmount() - sum);
        // things now add up correctly
    }

    @Override
    boolean validate() {
        // add extra logic here
        return super.validate();
    }
}
