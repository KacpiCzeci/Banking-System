package com.company.Report.IntermediateData;

import com.company.Bank;

import java.math.BigDecimal;

public class UserBankProductData {
    private final String bankProductType;
    private final String bankProductTID;
    private final BigDecimal balance;

    public UserBankProductData(String bankProductType, String bankProductTID, BigDecimal balance){
        this.bankProductType = bankProductType;
        this.bankProductTID = bankProductTID;
        this.balance = balance;
    }

    public String getBankProductType() {
        return bankProductType;
    }

    public String getBankProductTID() {
        return bankProductTID;
    }

    public BigDecimal getBalance() {
        return balance;
    }
}
