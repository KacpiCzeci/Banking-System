package com.company.Report;

import com.company.BankProduct.Account;
import com.company.Transaction.CashTransaction;
import com.company.Transaction.Transaction;
import com.company.Transaction.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class StatisticReport extends Report{
    public StatisticReport(){
        super(ReportType.STATISTIC);
    }
    @Override
    public void generateRaport(ArrayList<Account> accounts) {
        StringBuilder content = new StringBuilder(new String(""));
        for (Account account: accounts) {
            String accStr = new String("=======================================\n");
            accStr = accStr + "Account: " + account.getId().toString() + "\n";
            accStr = accStr + "Owner: " + account.getOwner().toString() + "\n";
            accStr = accStr + "Money: " + account.getBalance().toString() + "\n";
            accStr = accStr + "Total number of operations: " + account.getHistoryOfOperations().size() + "\n";
            accStr = accStr + "Total money received: " + account.getHistoryOfOperations().stream()
                    .filter(tran -> tran.getType().equals(TransactionType.RECEIVE)).map(tran -> ((CashTransaction)tran).getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add) + "\n";
            accStr = accStr + "Total money spend: " + account.getHistoryOfOperations().stream()
                    .filter(tran -> tran.getType().equals(TransactionType.PAYMENT)).map(tran -> ((CashTransaction)tran).getAmount()).reduce(BigDecimal.ZERO, BigDecimal::add) + "\n";
            accStr = accStr + "=======================================\n";
            content.append(accStr);
        }
        this.content = content.toString();
        this.generated = LocalDateTime.now();
    }
}
