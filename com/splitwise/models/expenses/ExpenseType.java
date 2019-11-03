package com.splitwise.models.expenses;

import com.splitwise.models.exceptions.InvalidExpenseTypeException;

public enum ExpenseType {
    EQUAL("equal"),
    PERCENT("percent"),
    EXACT("exact");

    String asString;
    ExpenseType(String asString) {
        this.asString = asString;
    }

    public static ExpenseType fromString(String type) throws InvalidExpenseTypeException {
        for(ExpenseType expType: ExpenseType.values()) {
            if(expType.asString.equals(type))
                return expType;
        }
        throw new InvalidExpenseTypeException("Invalid Expense Type " + type);
    }
}
