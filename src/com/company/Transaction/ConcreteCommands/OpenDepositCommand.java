package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.Account;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;

import java.time.LocalDateTime;

public class OpenDepositCommand implements TransactionCommand {
    protected final TransactionType type;
    protected final LocalDateTime dateOfTransaction = LocalDateTime.now();
    protected String description = "";
    private final Account account;
    private final String id;
    private final Integer time;

    public OpenDepositCommand(TransactionType type, Account account, String id, Integer time){
        this.type = type;
        this.account = account;
        this.id = id;
        this.time = time;
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
            this.account.openDeposit(this.id, this.time);
            this.createDescription();
        }
    }

    @Override
    public void createDescription() {
        this.description =  "Open Deposit " + this.account.getDeposit(this.id).getId() + " to account " + account.getId() + ".";
    }
}
