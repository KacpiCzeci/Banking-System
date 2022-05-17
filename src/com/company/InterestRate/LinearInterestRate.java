package com.company.InterestRate;

import com.company.BankProduct.BankProduct;

import java.math.BigDecimal;

public class LinearInterestRate implements InterestRate{
    private BankProduct context;

    public LinearInterestRate(BankProduct context){
        this.context = context;
    }

    @Override
    public void setContext(BankProduct context) {
        this.context = context;
    }

    @Override
    public BigDecimal calculateInterestRate(BigDecimal rateValue, BigDecimal principal, BigDecimal time) {
        return principal.multiply(time.multiply(rateValue));
    }
}
