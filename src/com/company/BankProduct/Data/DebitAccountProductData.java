package com.company.BankProduct.Data;

import com.company.Bank;
import com.company.BankProduct.BankProductType;
import com.company.TransferVerification.TransferVerification;
import com.company.User;

import java.math.BigDecimal;

public class DebitAccountProductData extends AccountProductData {
    private final BigDecimal debitLimit;

    public DebitAccountProductData(String id, Bank bank, BankProductType type, User owner, TransferVerification trVr, BigDecimal debitLimit){
        super(id, bank, type, owner, trVr);
        this.debitLimit = debitLimit;
    }

    public BigDecimal getDebitLimit() {
        return debitLimit;
    }
}
