package com.company.BankProduct;

import com.company.BankProduct.Data.*;

public class BankProductFactory {
    public BankProduct create(BankProductType type, BankProductData bankProductData){
        switch (type){
            case ACCOUNT:
                AccountProductData aPD = (AccountProductData) bankProductData;
                return new Account(aPD.getId(), aPD.getBank(), aPD.getOwner(), aPD.getTransferVerification());
            case DEBITACCOUNT:
                DebitAccountProductData dAPD = (DebitAccountProductData) bankProductData;
                return new DebitAccount(dAPD.getId(), dAPD.getBank(), dAPD.getOwner(), dAPD.getTransferVerification(), dAPD.getDebitLimit());
            case DEPOSIT:
                DepositData dD = (DepositData) bankProductData;
                return new Deposit(dD.getId(), dD.getBank(), dD.getOwner(), dD.getCloseDate());
            case LOAN:
                LoanData lD = (LoanData) bankProductData;
                return new Loan(lD.getId(), lD.getBank(), lD.getOwner(), lD.getCloseDate());
            default:
                return null;
        }
    }
}
