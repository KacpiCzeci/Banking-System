package com.company;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private Integer id;
    private String name;
    private Double totalMoney;
    private ArrayList<Account> accounts;
    private InterestRate interestRate;
    private ArrayList<Report> reports;
    private ArrayList<InterBankPayment> interBankPayments;

    public Bank(Integer id, String name, Double aR, Double dR, Double lR){
        this.id = id;
        this.name = name;
        this.totalMoney = 0.0;
        this.accounts = new ArrayList<Account>();
        this.interestRate = new InterestRate(aR, dR, lR);
        this.reports = new ArrayList<Report>();
        this.interBankPayments = new ArrayList<InterBankPayment>();
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getTotalMoney() {
        return totalMoney;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public ArrayList<Report> getReports() {
        return reports;
    }

    public InterestRate getInterestRate() {
        return interestRate;
    }

    public ArrayList<InterBankPayment> getInterBankPayments() {
        return interBankPayments;
    }

    public void createAccount(String owner, TransferVerification trVr){
        accounts.add(new Account(this, owner, trVr));
    }

    public void deleteAccount(Integer id){
        accounts.removeIf(account -> account.getId().equals(id));
    }

    public void makeInterBankPayments(){
        for (InterBankPayment payment: interBankPayments) {
            payment.getPaymentBank().takeInterbankPayment(payment.getTransaction());
        }
        interBankPayments.clear();
    }

    public void takeInterbankPayment(Transaction transaction){
        for (Account account: accounts) {
            if(account.equals(transaction.getReceiverAccount())){
                account.makePayment(transaction);
                return;
            }
        }
    }

    public void generateReport(String type){
        StringBuilder content = new StringBuilder(new String(""));
        switch (type){
            case "payment":
                for (Account account: accounts) {
                    String accStr = new String("=======================================\n");
                    accStr = accStr + "Account: " + account.getId().toString() + "\n";
                    accStr = accStr + "Owner: " + account.getOwner().toString() + "\n";
                    accStr = accStr + "Money: " + account.getTotalMoney().toString() + "\n";
                    accStr = accStr + "History of operations: \n";
                    for (Transaction transaction: account.getHistoryOfOperations()) {
                        accStr = accStr + transaction.getDateOdTransaction().toString() + " ";
                        accStr = accStr + transaction.getAmount().toString() + " ";
                        accStr = accStr + "to " + transaction.getReceiver().getOwner().toString() + "\n";
                    }
                    accStr = accStr + "=======================================\n";
                    content.append(accStr);
                }
                reports.add(new Report(type, content.toString()));
                break;
            case "statistic":
                for (Account account: accounts) {
                    String accStr = new String("=======================================\n");
                    accStr = accStr + "Account: " + account.getId().toString() + "\n";
                    accStr = accStr + "Owner: " + account.getOwner().toString() + "\n";
                    accStr = accStr + "Money: " + account.getTotalMoney().toString() + "\n";
                    accStr = accStr + "Total number of operations: " + account.getHistoryOfOperations().size() + "\n";
                    accStr = accStr + "Total money received: " + account.getHistoryOfOperations().stream()
                            .filter(tran -> tran.getReceiver().equals(account)).mapToDouble(Transaction::getAmount).sum() + "\n";
                    accStr = accStr + "Total money spend: " + account.getHistoryOfOperations().stream()
                            .filter(tran -> tran.getSender().equals(account)).mapToDouble(Transaction::getAmount).sum() + "\n";
                    accStr = accStr + "=======================================\n";
                    content.append(accStr);
                }
                reports.add(new Report(type, content.toString()));
                break;
            default:
                break;
        }
    }
}
