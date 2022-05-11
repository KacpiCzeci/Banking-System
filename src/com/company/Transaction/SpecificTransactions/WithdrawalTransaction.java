package com.company.Transaction.SpecificTransactions;

import com.company.Transaction.CashTransaction;
import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionType;

import java.math.BigDecimal;

public class WithdrawalTransaction extends CashTransaction {

    public WithdrawalTransaction(TransactionType type, BigDecimal amount){
        super(type,amount);
    }

    @Override
    protected String createDescription() {
        return "Withdrawal in amount of " + this.getAmount().toString() + ".";
    }
}