package com.company.InterestRate;

import com.company.BankProduct.BankProduct;

import java.math.BigDecimal;

public interface InterestRate {

    public void setContext(BankProduct context);
    public BigDecimal calculateInterestRate(BigDecimal rateValue, BigDecimal principal, BigDecimal time);
}
