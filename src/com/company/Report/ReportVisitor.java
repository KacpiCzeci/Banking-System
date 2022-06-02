package com.company.Report;

import com.company.BankProduct.Account;
import com.company.BankProduct.BankProduct;
import com.company.Transaction.TransactionCommand;
import com.company.User;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface ReportVisitor {
    public ReportType getType();
    public String getContent();
    public LocalDateTime getGenerated();
    public void generateReport();

    public abstract void visit(BankProduct bankProduct);
    public abstract void visit(TransactionCommand transactionCommand);
    public abstract void visit(User user);
}
