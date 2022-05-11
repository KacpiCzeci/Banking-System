package com.company.Transaction.SpecificTransactions;

import com.company.Transaction.CashTransaction;
import com.company.Transaction.TransactionType;

import java.math.BigDecimal;

public class InterestRateCalculationTransaction extends CashTransaction {

    public InterestRateCalculationTransaction(TransactionType type, BigDecimal amount){
        super(type,amount);
    }

    @Override
    protected String createDescription() {
        return "Received interests in amount of " + this.getAmount().toString() + ".";
    }
}
