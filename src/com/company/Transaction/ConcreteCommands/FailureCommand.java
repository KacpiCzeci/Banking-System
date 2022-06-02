package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.Account;
import com.company.BankProduct.BankProduct;
import com.company.BankProduct.BankProductType;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;

import java.time.LocalDateTime;

public class FailureCommand implements TransactionCommand {
    private final TransactionType type;
    private final LocalDateTime dateOfTransaction = LocalDateTime.now();
    private String description = "";
    private final TransactionCommand transaction;
    private final BankProduct receiver;

    public FailureCommand(TransactionType type, TransactionCommand transaction, BankProduct receiver){
        this.type = type;
        this.transaction = transaction;
        this.receiver = receiver;
    }

    public TransactionCommand getCommand(){
        return this.transaction;
    }

    public BankProduct getReceiver() {
        return receiver;
    }

    @Override
    public TransactionType getType() {
        return this.type;
    }

    @Override
    public LocalDateTime getDateOfTransaction() {
        return this.dateOfTransaction;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public void execute() {
        receiver.handleFailure(this.transaction);
        this.createDescription();
    }

    @Override
    public void createDescription() {
        this.description = "Failure of transaction: " + this.transaction.getType().name() + ".";
    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visit(this);
    }
}
