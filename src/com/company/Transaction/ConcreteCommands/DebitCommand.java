package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.DebitAccount;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DebitCommand implements TransactionCommand {
    protected final TransactionType type;
    protected final LocalDateTime dateOfTransaction = LocalDateTime.now();
    protected String description = "";
    private final DebitAccount account;
    private final BigDecimal amount;

    public DebitCommand(TransactionType type, DebitAccount account, BigDecimal amount){
        this.type = type;
        this.account = account;
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
            this.account.takeDebit(amount);
            this.createDescription();
        }
    }

    @Override
    public void createDescription() {
        this.description =  "Taken debit from " + this.account.getId() + " in amount of " + this.amount + ".";
    }
}
