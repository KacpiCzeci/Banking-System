package com.company.BankProduct.Data;

import com.company.Bank;
import com.company.BankProduct.BankProductType;
import com.company.User;

public abstract class BankProductData {
    protected final String id;
    protected final BankProductType type;
    protected final User owner;
    protected final Bank bank;

    public BankProductData(String id, Bank bank, BankProductType type, User owner){
        this.id = id;
        this.bank = bank;
        this.type = type;
        this.owner = owner;
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
}
