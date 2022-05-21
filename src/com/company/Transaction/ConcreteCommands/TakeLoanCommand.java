package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TakeLoanCommand implements TransactionCommand {
    protected final TransactionType type;
    protected final LocalDateTime dateOfTransaction = LocalDateTime.now();
    protected String description = "";
    private final Account account;
    private final String id;
    private final BigDecimal amount;

    public TakeLoanCommand(TransactionType type, Account account, String id, BigDecimal amount){
        this.type = type;
        this.account = account;
        this.id = id;
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
        TransferVerification transferVerification = new TransferVerification();
        if(transferVerification.verify(this)){
            account.takeMoneyFromLoan(this.id, this.amount);
            this.createDescription();
        }
    }

    @Override
    public void createDescription() {
        this.description =  "Take loan in amount of " + this.amount + ".";
    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visitTransaction(this);
    }
}
