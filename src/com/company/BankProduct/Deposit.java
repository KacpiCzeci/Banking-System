package com.company.BankProduct;

import com.company.Bank;
import com.company.Report.ReportVisitor;
import com.company.Transaction.TransactionCommand;
import com.company.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Deposit extends BankProduct {
    private final LocalDateTime closeDate;

    public Deposit(String id, Bank bank, User user, LocalDateTime closeDate){
        super(id, bank, BankProductType.DEPOSIT, user, BankProductStatus.ACTIVE);
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

    public void depositMoney(BigDecimal amount){
        receiveMoney(amount);
    }

    public BigDecimal closeDeposit(){
        BigDecimal returnMoney = null;
        if(LocalDateTime.now().isBefore(this.closeDate)){
            returnMoney = this.withdrawMoney(this.balance);
        }
        else {
            BigDecimal time = new BigDecimal(String.valueOf(ChronoUnit.MONTHS.between(this.dateOfOpening, closeDate)));
            returnMoney = this.withdrawMoney(this.balance).add(interestRate.calculateInterestRate(new BigDecimal("0.03"), this.balance, time));
        }
        this.status = BankProductStatus.CLOSED;
        return returnMoney;
    }
}
