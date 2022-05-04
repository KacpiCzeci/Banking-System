package com.company;

public class InterestRate {
    private Double accountRate;
    private Double depositRate;
    private Double loanRate;

    public InterestRate(Double aR, Double dR, Double lR){
        this.accountRate = aR;
        this.depositRate = dR;
        this.loanRate = lR;
    }

    public Double getAccountRate() {
        return accountRate;
    }

    public Double getDepositRate() {
        return depositRate;
    }

    public Double getLoanRate() {
        return loanRate;
    }

    public Double calculateInterestRate(String nameRate, Double principal, Double time){
        if(principal<0.0) {
            return -1.0;
        }
        switch (nameRate){
            case "account":
                return Math.round(principal * accountRate * time * 100) / 100.0;
            case "deposit":
                return Math.round(principal * depositRate * time * 100) / 100.0;
            case "loan":
                return  Math.round(principal * loanRate * time * 100) / 100.0;
            default:
                return Math.round(principal * 1.0 * 100) / 100.0;
        }
    }
}
