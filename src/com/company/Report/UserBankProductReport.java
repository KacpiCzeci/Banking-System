package com.company.Report;

import com.company.BankProduct.BankProduct;
import com.company.Report.IntermediateData.UserBankProductData;
import com.company.Transaction.TransactionCommand;
import com.company.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class UserBankProductReport implements ReportVisitor{
    private ReportType type;
    private String content = "";
    private LocalDateTime generated = null;
    private String user = "";
    private ArrayList<UserBankProductData> products = new ArrayList<>();

    public UserBankProductReport(ReportType type){
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
        for (UserBankProductData product: products) {
            content = content + "=======================================\n";
            content = content + "Bank Product: " + product.getBankProductTID() + "\n";
            content = content + "Type: " + product.getBankProductType() + "\n";
            content = content + "Balance: " + product.getBalance().toString() + "\n";
        }
        content = content + "=======================================\n";
        this.content = content;
        this.generated = LocalDateTime.now();
    }

    @Override
    public void visitBankProduct(BankProduct bankProduct) {
        this.products.add(new UserBankProductData(bankProduct.getType().name(), bankProduct.getId(), bankProduct.getBalance()));
    }

    @Override
    public void visitTransaction(TransactionCommand transactionCommand) {

    }

    @Override
    public void visitUser(User user) {
        this.user = user.getId();
    }
}
