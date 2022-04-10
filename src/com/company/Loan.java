package com.company;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Loan {
    private Long id;
    private Double amount;
    private LocalDateTime startDate;
    private LocalDateTime closeDate;
    private InterestRate loanRate;

    public Loan(Long id, Double amt, LocalDateTime cD, InterestRate lR){
        this.id = id;
        this.amount = amt;
        this.startDate = LocalDateTime.now();
        this.closeDate = cD;
        this.loanRate = lR;
    }

    public Long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getCloseDate() {
        return closeDate;
    }

    public Double getLoan(){
        Double time = (double) ChronoUnit.YEARS.between(startDate, closeDate);
        return amount + loanRate.calculateInterestRate("loan", amount, time);
    }

    public void payLoan(){
        this.amount = 0.00;
    }
}
