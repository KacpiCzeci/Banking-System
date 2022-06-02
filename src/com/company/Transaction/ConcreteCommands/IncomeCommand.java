package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.BankProduct;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class IncomeCommand implements TransactionCommand {
    protected final TransactionType type;
    protected final LocalDateTime dateOfTransaction = LocalDateTime.now();
    protected String description = "";
    private final BankProduct receiver;
    private final BigDecimal amount;

    public IncomeCommand(TransactionType type, BankProduct receiver, BigDecimal amount){
        this.type = type;
        this.receiver = receiver;
        this.amount = amount;
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
        this.receiver.receiveMoney(amount);
        this.createDescription();
    }

    @Override
    public void createDescription() {
        this.description =  "Income in amount of " + this.amount + ".";
    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visit(this);
    }
}
