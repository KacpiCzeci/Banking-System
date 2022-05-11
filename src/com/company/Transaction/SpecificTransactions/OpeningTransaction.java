package com.company.Transaction.SpecificTransactions;

import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionType;

public class OpeningTransaction extends Transaction {
    private final String productName;

    public OpeningTransaction(TransactionType type, String productName){
        super(type);
        this.productName = productName;
    }

    public String getProductName() {
        return productName;
    }

    @Override
    protected String createDescription() {
        return "Opened " + this.productName + ".";
    }
}
