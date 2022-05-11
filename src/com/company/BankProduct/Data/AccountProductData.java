package com.company.BankProduct.Data;

import com.company.Bank;
import com.company.BankProduct.BankProductType;
import com.company.TransferVerification.TransferVerification;
import com.company.User;

public class AccountProductData extends BankProductData {
    private final TransferVerification transferVerification;

    public AccountProductData(String id, Bank bank, BankProductType type, User owner, TransferVerification trVr){
        super(id, bank, type, owner);
        this.transferVerification = trVr;
    }

    public TransferVerification getTransferVerification() {
        return transferVerification;
    }
}
