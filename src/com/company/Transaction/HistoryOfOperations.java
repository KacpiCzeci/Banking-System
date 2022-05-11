package com.company.Transaction;

import com.company.Transaction.Transaction;

import java.util.ArrayList;

public class HistoryOfOperations {
    private final ArrayList<Transaction> history = new ArrayList<>();

    public HistoryOfOperations(){
    }

    public void addOperation(Transaction transaction){
        history.add(transaction);
    }

    public ArrayList<Transaction> getHistory() {
        return history;
    }
}
