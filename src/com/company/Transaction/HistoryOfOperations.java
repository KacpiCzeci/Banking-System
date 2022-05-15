package com.company.Transaction;

import com.company.Transaction.Transaction;

import java.util.ArrayList;

public class HistoryOfOperations {
    private final ArrayList<TransactionCommand> history = new ArrayList<>();

    public HistoryOfOperations(){
    }

    public void addOperation(TransactionCommand transaction){
        history.add(transaction);
    }

    public ArrayList<TransactionCommand> getHistory() {
        return history;
    }
}
