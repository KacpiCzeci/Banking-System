package com.company.BankProduct;

import com.company.Bank;
import com.company.User;

public abstract class BankProductDecorator extends BankProduct {
    protected final BankProduct bankProduct;
    public BankProductDecorator(String id, Bank bank, BankProductType type, User owner, BankProductStatus status, BankProduct bankProduct) {
        super(id, bank, type, owner, status);
        this.bankProduct = bankProduct;
    }

    public BankProduct getBankProduct() {
        return bankProduct;
    }
}
