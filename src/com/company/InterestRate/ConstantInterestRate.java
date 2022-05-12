package com.company.InterestRate;

import com.company.BankProduct.BankProduct;

import java.math.BigDecimal;

public class ConstantInterestRate implements InterestRate{
    private BankProduct context;

    public ConstantInterestRate(BankProduct context){
        this.context = context;
    }

    @Override
    public void setContext(BankProduct context) {
        this.context = context;
    }

    @Override
    public BigDecimal calculateInterestRate(BigDecimal rateValue, BigDecimal principal, BigDecimal time) {
        if(time.compareTo(new BigDecimal("5")) < 0){
            return principal.multiply(time.multiply(rateValue));
        }
        else if (time.compareTo(new BigDecimal("10")) < 0){
            return principal.multiply((new BigDecimal("5")).multiply(rateValue));
        }
        else {
            return principal.multiply(time.multiply(rateValue));
        }
    }
}
