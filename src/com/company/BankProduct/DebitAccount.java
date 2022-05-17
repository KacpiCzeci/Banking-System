package com.company.BankProduct;

import com.company.Bank;
import com.company.Transaction.SpecificTransactions.DebitTransaction;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;
import com.company.User;

import java.math.BigDecimal;

public class DebitAccount extends Account {
    private BigDecimal debitLimit;
    private BigDecimal debit = new BigDecimal("0.00");

    public DebitAccount(String id, Bank bank, User user, TransferVerification trVr, BigDecimal debitLimit){
        super(id, bank, user, trVr);
        this.debitLimit = debitLimit;
    }

    public BigDecimal getDebitLimit() {
        return this.debitLimit;
    }

    public void setDebitLimit(BigDecimal debit) {
        this.debitLimit = debit;
    }

    public BigDecimal getDebit() {
        return this.debit;
    }

    @Override
    public void receiveMoney(BigDecimal amount){
        if(this.debit.compareTo(new BigDecimal("0.00")) > 0){
            if(this.debit.compareTo(amount) <= 0){
                this.balance = new BigDecimal(String.valueOf(this.balance.add(amount.subtract(this.debit))));
                this.debit = new BigDecimal("0.00");
            }
            if(this.debit.compareTo(amount) > 0){
                this.balance = new BigDecimal(String.valueOf(this.debit.subtract(amount)));
            }
        }
    }

//    @Override
//    public void handleFailure(TransactionCommand transactionCommand) {
//
//    }

    public void takeDebit(BigDecimal amount){
        if(amount.add(this.debit).compareTo(this.debitLimit) <= 0){
            this.debit = new BigDecimal(String.valueOf(this.debit.add(amount)));
            this.balance = new BigDecimal(String.valueOf(this.balance.add(amount)));
        }
    }
}
