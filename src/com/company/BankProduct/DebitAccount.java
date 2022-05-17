package com.company.BankProduct;

import com.company.Bank;
import com.company.Transaction.TransactionCommand;
import com.company.User;

import java.math.BigDecimal;

public class DebitAccount extends BankProductDecorator {
    private BigDecimal debitLimit = new BigDecimal("0.00");
    private BigDecimal debit = new BigDecimal("0.00");

    public DebitAccount(String id, Bank bank, User owner, Account bankProduct){
        super(id, bank, BankProductType.DEBITACCOUNT, owner, BankProductStatus.ACTIVE, bankProduct);
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
                this.bankProduct.receiveMoney(amount.subtract(this.debit));
                this.debit = new BigDecimal("0.00");
            }
            if(this.debit.compareTo(amount) > 0){
                this.debit = this.debit.subtract(amount);
            }
        }
    }

    @Override
    public BigDecimal withdrawMoney(BigDecimal amount) {
        if(this.bankProduct.getBalance().compareTo(amount) < 0){
            BigDecimal diff = amount.subtract(this.bankProduct.getBalance());
            if(diff.add(debit).compareTo(this.debitLimit) <= 0){
                this.debit = this.debit.add(diff);
                return this.bankProduct.withdrawMoney(bankProduct.getBalance());
            }
            else{
                return null;
            }
        }
        else{
            return this.bankProduct.withdrawMoney(amount);
        }
    }

    @Override
    public void doTransaction(TransactionCommand transactionCommand) {
        transactionCommand.execute();
    }

    @Override
    public void handleFailure(TransactionCommand transactionCommand) {

    }
}
