package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.Account;
import com.company.BankProduct.BankProduct;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PaymentCommand implements TransactionCommand {
    private final TransactionType type;
    private final LocalDateTime dateOfTransaction = LocalDateTime.now();
    private String description = "";
    private final BankProduct sender;
    private final BankProduct receiver;
    private final BigDecimal amount;

    public PaymentCommand(TransactionType type, BankProduct sender, BankProduct receiver, BigDecimal amount){
        this.type = type;
        this.sender = sender;
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
        TransferVerification transferVerification = new TransferVerification();
        if(transferVerification.verify(this)){
            this.sender.withdrawMoney(amount);
            this.receiver.receiveMoney(amount);
            this.createDescription();
        }
    }

    @Override
    public void createDescription(){
        this.description = "Payment from " + this.sender.getId() + " to " + this.receiver.getId() + " in amount of " + this.amount + ".";
    }
}
