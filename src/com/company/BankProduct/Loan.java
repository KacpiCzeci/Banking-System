package com.company.BankProduct;

import com.company.Bank;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Loan extends BankProduct {
    private final LocalDateTime closeDate;

    public Loan(String id, Bank bank, User user, LocalDateTime closeDate){
        super(id, bank, BankProductType.LOAN, user, BankProductStatus.ACTIVE);
        this.closeDate = closeDate;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    @Override
    public void receiveMoney(BigDecimal money) {
        this.balance = new BigDecimal(String.valueOf(this.balance.add(money)));
    }

    @Override
    public BigDecimal withdrawMoney(BigDecimal money){
        this.balance = new BigDecimal(String.valueOf(this.balance.subtract(money)));
        return money;
    }

    @Override
    public void doTransaction(TransactionCommand transactionCommand) {
        transactionCommand.execute();
    }

    @Override
    public void handleFailure(TransactionCommand transactionCommand) {

    }

    @Override
    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visitBankProduct(this);
    }

    public BigDecimal takeLoan(BigDecimal amount){
        this.receiveMoney(amount);
        return amount;
    }

    public BigDecimal payLoan(){
        BigDecimal returnMoney = null;
        BigDecimal time = new BigDecimal(String.valueOf(ChronoUnit.MONTHS.between(this.dateOfOpening, closeDate)));
        returnMoney = this.withdrawMoney(this.balance).add(interestRate.calculateInterestRate(new BigDecimal("0.03"), this.balance, time));
        this.status = BankProductStatus.CLOSED;
        return returnMoney;
    }
}
