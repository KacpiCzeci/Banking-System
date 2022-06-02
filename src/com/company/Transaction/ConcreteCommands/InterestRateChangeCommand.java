package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.BankProduct;
import com.company.InterestRate.InterestRate;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InterestRateChangeCommand implements TransactionCommand {
    protected final TransactionType type;
    protected final LocalDateTime dateOfTransaction = LocalDateTime.now();
    protected String description = "";
    private final BankProduct receiver;
    private final InterestRate interestRate;

    public InterestRateChangeCommand(TransactionType type, BankProduct receiver, InterestRate interestRate){
        this.type = type;
        this.receiver = receiver;
        this.interestRate = interestRate;
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
        this.receiver.changeInterestRate(interestRate);
        this.createDescription();
    }

    @Override
    public void createDescription() {
        this.description =  "Changed interest rates.";
    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visitTransaction(this);
    }
}
