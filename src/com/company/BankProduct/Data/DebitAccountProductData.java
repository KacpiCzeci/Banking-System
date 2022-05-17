package com.company.BankProduct.Data;

import com.company.Bank;
import com.company.BankProduct.Account;
import com.company.BankProduct.BankProduct;
import com.company.BankProduct.BankProductStatus;
import com.company.BankProduct.BankProductType;
import com.company.TransferVerification.TransferVerification;
import com.company.User;

import java.math.BigDecimal;

public class DebitAccountProductData extends BankProductData {
    private final Account account;

    public DebitAccountProductData(String id, Bank bank, BankProductType type, User owner, Account account){
        super(id, bank, type, owner);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
