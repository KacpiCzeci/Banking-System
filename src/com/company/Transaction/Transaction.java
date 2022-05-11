package com.company.Transaction;

import java.time.LocalDateTime;

public abstract class Transaction {
    protected final TransactionType type;
    protected final LocalDateTime dateOfTransaction = LocalDateTime.now();
    protected final String description = createDescription();

    public Transaction(TransactionType type){
        this.type = type;
    }

    public TransactionType getType() {
        return type;
    }

    public LocalDateTime getDateOfTransaction() {
        return dateOfTransaction;
    }

    public String getDescription() {
        return description;
    }

    protected abstract String createDescription();
}
