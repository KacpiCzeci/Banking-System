package com.company.Report;

import com.company.BankProduct.Account;

import java.time.LocalDateTime;
import java.util.ArrayList;

public abstract class Report {
    protected ReportType type;
    protected String content;
    protected LocalDateTime generated;

    public Report(ReportType type) {
        this.type = type;
        this.content = "";
        this.generated = null;
    }

    public ReportType getType(){
        return this.type;
    };
    public String getContent(){
        return this.content;
    };

    public LocalDateTime getGenerated(){
        return this.generated;
    };

    public abstract void generateRaport(ArrayList<Account> accounts);
}
