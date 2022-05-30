package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.Account;
import com.company.BankProduct.BankProduct;
import com.company.Card.Card;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class AddCardCommand implements TransactionCommand {
    private final TransactionType type;
    private final LocalDateTime dateOfTransaction = LocalDateTime.now();
    private String description = "";
    private final Account account;
    private final Card card;

    public AddCardCommand(TransactionType type, Account account, Card card){
        this.type = type;
        this.account = account;
        this.card = card;
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
        this.account.addCard(this.card);
        this.createDescription();
    }

    @Override
    public void createDescription() {
        this.description =  "Added new card " + this.card.getNumber() + ".";
    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visitTransaction(this);
    }
}
