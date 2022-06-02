package com.company;

import com.company.Report.ReportVisitor;

public class User {
    private final String id;
    private UserStatus status = UserStatus.ACTIVE;
    private final Bank bank;

    public User(String id, Bank bank){
        this.bank = bank;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public UserStatus getStatus() {
        return status;
    }

    public Bank getBank() {
        return bank;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public void acceptVisitor(ReportVisitor reportVisitor) {
        reportVisitor.visit(this);
    }
}
