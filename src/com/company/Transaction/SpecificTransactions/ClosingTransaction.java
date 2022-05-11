package com.company.Transaction.SpecificTransactions;

import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionType;

public class ClosingTransaction extends Transaction {
    private final String productName;
    private final String additionalInfo;

    public ClosingTransaction(TransactionType type, String productName, String additionalInfo){
        super(type);
        this.productName = productName;
        this.additionalInfo = additionalInfo;
    }

    public String getProductName() {
        return productName;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    @Override
    protected String createDescription() {
        return "Closed " + this.productName + (this.additionalInfo.isEmpty()? "": " - " + this.additionalInfo) + ".";
    }
}
