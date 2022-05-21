package com.company.Report;

import com.company.BankProduct.Account;
import com.company.BankProduct.BankProduct;
import com.company.Report.IntermediateData.StatisticData;
import com.company.Transaction.CashTransaction;
import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StatisticReport implements ReportVisitor{
    private ReportType type;
    private String content = "";
    private LocalDateTime generated = null;
    private Integer user = 0;
    private BigDecimal balance = new BigDecimal("0.00");
    private Integer transactionAmount = 0;
    private Integer accountAmount = 0;

    public StatisticReport(ReportType type) {
        this.type = type;
    }

    @Override
    public void generateReport() {
        String content = "";
        content = content + "=======================================\n";
        content = content + "Total amount of users: " + this.user.toString() + "\n";
        content = content + "Total balance: " + this.balance.toString() + "\n";
        content = content + "Total number of operations: " + this.transactionAmount.toString() + "\n";
        content = content + "Total number of bank products: " + this.accountAmount.toString() + "\n";
        this.content = content;
        this.generated = LocalDateTime.now();
    }

    @Override
    public ReportType getType() {
        return this.type;
    }

    @Override
    public String getContent() {
        return this.content;
    }

    @Override
    public LocalDateTime getGenerated() {
        return this.generated;
    }

    @Override
    public void visitBankProduct(BankProduct bankProduct) {
        this.accountAmount++;
        this.balance = this.balance.add(bankProduct.getBalance());
    }

    @Override
    public void visitTransaction(TransactionCommand transactionCommand) {
        this.transactionAmount++;
    }

    @Override
    public void visitUser(User user) {
        this.user++;
    }
}
