package com.company.Report;

import com.company.BankProduct.BankProduct;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class UserPaymentReport implements ReportVisitor {
    private ReportType type;
    private String content = "";
    private LocalDateTime generated = null;
    private String user = "";
    private Integer paymentT = 0;
    private Integer withdrawalT = 0;
    private Integer allT = 0;
    private BigDecimal allBalance = new BigDecimal("0.00");
    private Integer allAcc = 0;

    public UserPaymentReport(ReportType type){
        this.type = type;
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
    public void generateReport() {
        String content = "";
        content = content + "=======================================\n";
        content = content + "User: " + this.user + "\n";
        content = content + "Total balance: " + this.allBalance.toString() + "\n";
        content = content + "Total number of payments: " + this.paymentT.toString() + "\n";
        content = content + "Total number of withdrawals: " + this.withdrawalT.toString() + "\n";
        content = content + "Total number of operations: " + this.allT.toString() + "\n";
        content = content + "Total number of bank products: " + this.allAcc.toString() + "\n";
        this.content = content;
        this.generated = LocalDateTime.now();
    }

    @Override
    public void visitBankProduct(BankProduct bankProduct) {
        this.allAcc++;
        this.allBalance = this.allBalance.add(bankProduct.getBalance());
    }

    @Override
    public void visitTransaction(TransactionCommand transactionCommand) {
        if(transactionCommand.getType().equals(TransactionType.PAYMENT)){
            this.paymentT++;
        }
        else if(transactionCommand.getType().equals(TransactionType.WITHDRAWAL)){
            this.withdrawalT++;
        }
        this.allT++;
    }

    @Override
    public void visitUser(User user) {
        this.user = user.getId();
    }
}
