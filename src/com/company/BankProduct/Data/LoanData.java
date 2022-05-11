package com.company.BankProduct.Data;

import com.company.Bank;
import com.company.BankProduct.BankProductType;
import com.company.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class LoanData extends BankProductData{
    private final BigDecimal balance;
    private final LocalDateTime closeDate;

    public LoanData(String id, Bank bank, BankProductType type, User owner, BigDecimal balance, LocalDateTime closeDate){
        super(id, bank, type, owner);
        this.balance = balance;
        this.closeDate = closeDate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }
}
