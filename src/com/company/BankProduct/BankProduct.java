package com.company.BankProduct;

import com.company.Bank;
import com.company.Transaction.Transaction;
import com.company.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class BankProduct {
    protected final String id;
    protected final BankProductType type;
    protected final User owner;
    protected final Bank bank;
    protected BigDecimal balance = new BigDecimal("0.00");
    protected BankProductStatus status;
    protected final LocalDateTime dateOfOpening = LocalDateTime.now();
    protected final ArrayList<Transaction> historyOfOperations = new ArrayList<>();

    public BankProduct(String id, Bank bank, BankProductType type, User owner, BankProductStatus status){
        this.id = id;
        this.bank = bank;
        this.type = type;
        this.owner = owner;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public Bank getBank() {
        return bank;
    }

    public BankProductType getType() {
        return type;
    }

    public User getOwner() {
        return owner;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BankProductStatus getStatus() {
        return status;
    }

    public void setStatus(BankProductStatus status) {
        this.status = status;
    }

    public LocalDateTime getDateOfOpening() {
        return dateOfOpening;
    }

    public ArrayList<Transaction> getHistoryOfOperations() {
        return historyOfOperations;
    }

    public void addOperationToHistory(Transaction transaction){
        this.historyOfOperations.add(transaction);
    }

    protected abstract void receiveMoney(BigDecimal money);

    protected abstract BigDecimal withdrawMoney(BigDecimal money);

}
