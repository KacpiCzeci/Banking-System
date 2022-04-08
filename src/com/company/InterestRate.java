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
        switch (nameRate){
            case "account":
                return principal * accountRate * time;
            case "deposit":
                return principal * depositRate * time;
            case "loan":
                return  principal * loanRate * time;
            default:
                return principal * 1.0 * time;
        }
    }
}
