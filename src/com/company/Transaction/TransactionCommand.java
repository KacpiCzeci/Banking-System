package com.company.Transaction;

import java.time.LocalDateTime;

public interface TransactionCommand {
    public TransactionType getType();
    public LocalDateTime getDateOfTransaction();
    public String getDescription();
    void execute();
    void createDescription();
}
