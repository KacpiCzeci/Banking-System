package com.company.Transaction.ConcreteCommands;

import com.company.Bank;
import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InterBankCommand implements TransactionCommand {
    protected TransactionType type;
    protected final LocalDateTime dateOfTransaction = LocalDateTime.now();
    protected String description = "";
    private final Bank senderBank;
    private final String receiverBank;
    private final String id;
    private final Account account;
    private final BigDecimal amount;

    public InterBankCommand(TransactionType type, Account account, Bank senderBank, String receiverBank, String id, BigDecimal amount){
        this.type = type;
        this.senderBank = senderBank;
        this.receiverBank = receiverBank;
        this.id = id;
        this.account = account;
        this.amount = amount;
    }

    public void setType(TransactionType type){
        if(type.equals(TransactionType.INTERBANKINCOME) || type.equals(TransactionType.INTERBANKPAYMENT)){
            this.type = type;
        }
    }

    public String getReceiverId() {
        return id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
    }

    public String getReceiverBank() {
        return receiverBank;
    }

    public Bank getSenderBank() {
        return senderBank;
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
        if(this.type.equals(TransactionType.INTERBANKPAYMENT)){
            this.account.withdrawMoney(this.amount);
            createDescription();
        }
        else if(this.type.equals(TransactionType.INTERBANKINCOME)){
            this.account.receiveMoney(this.amount);
            createDescription();
        }
    }

    @Override
    public void createDescription() {
        this.description = "InterBankPayment from " + this.senderBank.getId() + " to " + this.receiverBank + " in amount of " + this.amount + ".";
    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visitTransaction(this);
    }
}
