package com.example.test.Model;

import io.realm.RealmObject;

public class ExpenseModel extends RealmObject {
    private String expenseName;
    private String expensePrice;

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getExpensePrice() {
        return expensePrice;
    }

    public void setExpensePrice(String expensePrice) {
        this.expensePrice = expensePrice;
    }
}
