package com.company.Transaction;

import com.company.Report.ReportVisitor;

import java.time.LocalDateTime;

public interface TransactionCommand {
    public TransactionType getType();
    public LocalDateTime getDateOfTransaction();
    public String getDescription();
    public void execute();
    public void createDescription();
    public void acceptVisitor(ReportVisitor reportVisitor);
}
