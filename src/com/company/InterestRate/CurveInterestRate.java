package com.company.InterestRate;

import com.company.BankProduct.BankProduct;

import java.math.BigDecimal;

public class CurveInterestRate implements InterestRate{
    private BankProduct context;

    public CurveInterestRate(BankProduct context){
        this.context = context;
    }

    @Override
    public void setContext(BankProduct context) {
        this.context = context;
    }

    @Override
    public BigDecimal calculateInterestRate(BigDecimal rateValue, BigDecimal principal, BigDecimal time) {
        return principal.multiply(time.pow(2).multiply(rateValue));
    }
}
