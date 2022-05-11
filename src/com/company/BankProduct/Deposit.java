package com.company.BankProduct;

import com.company.Bank;
import com.company.Transaction.SpecificTransactions.ClosingTransaction;
import com.company.Transaction.SpecificTransactions.PaymentTransaction;
import com.company.Transaction.TransactionType;
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
    protected void receiveMoney(BigDecimal money) {
        this.balance = new BigDecimal(String.valueOf(this.balance.add(money)));
    }

    @Override
    protected BigDecimal withdrawMoney(BigDecimal money){
        this.balance = new BigDecimal(String.valueOf(this.balance.subtract(money)));
        return money;
    }

    public void depositMoney(BigDecimal amount){
        PaymentTransaction paymentTransaction = new PaymentTransaction(TransactionType.PAYMENT, amount, this.owner.getId(), this.owner.getId());
        this.addOperationToHistory(paymentTransaction);
        this.bank.addToHistory(paymentTransaction);
        receiveMoney(amount);
    }

    public BigDecimal closeDeposit(){
        BigDecimal returnMoney = null;
        ClosingTransaction closingTransaction = new ClosingTransaction(TransactionType.CLOSING, "Deposit", "Regular closing");
        this.addOperationToHistory(closingTransaction);
        this.bank.addToHistory(closingTransaction);
        if(LocalDateTime.now().isBefore(this.closeDate)){
            returnMoney = this.withdrawMoney(this.balance);
        }
        else {
            Double time = (double) ChronoUnit.MONTHS.between(this.dateOfOpening, closeDate);
            returnMoney = this.withdrawMoney(this.balance).add(interestRate.calculateInterestRate("deposit", this.balance, time));
        }
        this.status = BankProductStatus.CLOSED;
        return returnMoney;
    }
}
