package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.Account;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;

import java.time.LocalDateTime;

public class CloseLoanCommand implements TransactionCommand {
    protected final TransactionType type;
    protected final LocalDateTime dateOfTransaction = LocalDateTime.now();
    protected String description = "";
    private final Account account;
    private final String id;

    public CloseLoanCommand(TransactionType type, Account account, String id){
        this.type = type;
        this.account = account;
        this.id = id;
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
            this.account.closeLoan(this.id);
            this.createDescription();
        }
    }

    @Override
    public void createDescription() {
        this.description =  "Closed Loan " + this.account.getLoan(this.id).getId() + " of account " + account.getId() + ".";
    }
}
