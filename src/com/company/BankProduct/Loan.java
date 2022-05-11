package com.company.BankProduct;

import com.company.Bank;
import com.company.InterestRate;
import com.company.Transaction.SpecificTransactions.ClosingTransaction;
import com.company.Transaction.SpecificTransactions.PaymentTransaction;
import com.company.Transaction.TransactionType;
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
    protected void receiveMoney(BigDecimal money) {
        this.balance = new BigDecimal(String.valueOf(this.balance.add(money)));
    }

    @Override
    protected BigDecimal withdrawMoney(BigDecimal money){
        this.balance = new BigDecimal(String.valueOf(this.balance.subtract(money)));
        return money;
    }

    public BigDecimal takeLoan(BigDecimal amount){
        PaymentTransaction paymentTransaction = new PaymentTransaction(TransactionType.PAYMENT, amount, this.owner.getId(), this.owner.getId());
        this.addOperationToHistory(paymentTransaction);
        this.bank.addToHistory(paymentTransaction);
        this.receiveMoney(amount);
        return amount;
    }

    public BigDecimal payLoan(){
        ClosingTransaction closingTransaction = new ClosingTransaction(TransactionType.CLOSING, "Loan", "Regular closing");
        this.addOperationToHistory(closingTransaction);
        this.bank.addToHistory(closingTransaction);
        Double time = (double) ChronoUnit.MONTHS.between(this.dateOfOpening, closeDate);
        BigDecimal returnMoney = this.withdrawMoney(this.balance).add(interestRate.calculateInterestRate("loan", this.balance, time));
        this.status = BankProductStatus.CLOSED;
        return returnMoney;
    }
}
