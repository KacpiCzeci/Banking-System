package com.company.Transaction.SpecificTransactions;

import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionType;

public class ReportTransaction extends Transaction {
    private final String typeOfReport;

    public ReportTransaction(TransactionType type, String typeOfReport){
        super(type);
        this.typeOfReport = typeOfReport;
    }

    public String getTypeOfReport() {
        return typeOfReport;
    }

    @Override
    protected String createDescription() {
        return "Created Report with type " + this.typeOfReport + ".";
    }
}