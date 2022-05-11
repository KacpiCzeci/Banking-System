package com.company.Report;

import com.company.BankProduct.Account;
import com.company.Transaction.Transaction;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PaymentReport extends Report{
    public PaymentReport(){
        super(ReportType.PAYMENT);
    }
    @Override
    public void generateRaport(ArrayList<Account> accounts) {
        StringBuilder content = new StringBuilder(new String(""));
        for (Account account: accounts) {
            String accStr = new String("=======================================\n");
            accStr = accStr + "Account: " + account.getId().toString() + "\n";
            accStr = accStr + "Owner: " + account.getOwner().toString() + "\n";
            accStr = accStr + "Money: " + account.getBalance().toString() + "\n";
            accStr = accStr + "History of operations: \n";
            for (Transaction transaction: account.getHistoryOfOperations()) {
                accStr = accStr + transaction.getDateOfTransaction().toString() + " ";
                accStr = accStr + transaction.getDescription() + "\n";
            }
            accStr = accStr + "=======================================\n";
            content.append(accStr);
        }
        this.content = content.toString();
        this.generated = LocalDateTime.now();
    }
}
