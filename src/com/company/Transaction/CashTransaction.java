package com.company.Transaction;
import com.company.Transaction.Transaction;

import java.math.BigDecimal;

public abstract class CashTransaction extends Transaction {
    private final BigDecimal amount;

    public CashTransaction(TransactionType type, BigDecimal amount){
        super(type);
        this.amount=amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }
}
