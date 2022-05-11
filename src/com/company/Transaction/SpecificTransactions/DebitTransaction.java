package com.company.Transaction.SpecificTransactions;

import com.company.Transaction.CashTransaction;
import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionType;

import java.math.BigDecimal;

public class DebitTransaction extends CashTransaction {

    public DebitTransaction(TransactionType type, BigDecimal amount){
        super(type,amount);
    }

    @Override
    protected String createDescription() {
        return "Taken debit from " + " in amount of " + this.getAmount().toString() + ".";
    }
}
