package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;

import java.time.LocalDateTime;

public class RemoveCard implements TransactionCommand {
    private final TransactionType type;
    private final LocalDateTime dateOfTransaction = LocalDateTime.now();
    private String description = "";
    private final Account account;
    private final Long number;

    public RemoveCard(TransactionType type, Account account, Long number) {
        this.type = type;
        this.account = account;
        this.number = number;
    }

    @Override
    public TransactionType getType() {
        return type;
    }

    @Override
    public LocalDateTime getDateOfTransaction() {
        return dateOfTransaction;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public void execute() {
        this.account.removeCard(this.number);
        this.createDescription();
    }

    @Override
    public void createDescription() {
        this.description =  "Removed card " + this.number + ".";
    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visitTransaction(this);
    }
}
