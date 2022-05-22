package com.company.Transaction.ConcreteCommands;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;

import javax.swing.plaf.basic.BasicIconFactory;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UseCardCommand implements TransactionCommand {
    private final TransactionType type;
    private final LocalDateTime dateOfTransaction = LocalDateTime.now();
    private String description = "";
    private final Account account;
    private final Long number;
    private final BigDecimal amount;

    public UseCardCommand(TransactionType type, Account account, Long number, BigDecimal amount) {
        this.type = type;
        this.account = account;
        this.number = number;
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
        this.account.useCardPayment(this.number, this.amount);
        this.createDescription();
    }

    @Override
    public void createDescription() {
        this.description =  "Paid by card " + this.number + " in amount of " + this.amount.toString() + ".";
    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visitTransaction(this);
    }
}
