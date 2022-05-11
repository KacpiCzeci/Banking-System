package com.company.Transaction.SpecificTransactions;

import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionType;

public class ChangeOfInterestRateTransaction extends Transaction {
    public ChangeOfInterestRateTransaction(TransactionType type){
        super(type);
    }

    @Override
    protected String createDescription() {
        return "Changed interest rates";
    }
}
