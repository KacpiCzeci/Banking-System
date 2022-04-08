package com.company;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Deposit {
    private Long id;
    private Double amount;
    private LocalDateTime startDate;
    private LocalDateTime closeDate;
    private InterestRate depsitRate;

    public Deposit(Long id, Double amt, LocalDateTime cD, InterestRate dR){
        this.id = id;
        this.amount = amt;
        this.startDate = LocalDateTime.now();
        this.closeDate = cD;
        this.depsitRate = dR;
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

    public Double withdrawMoney(Double money){
        if(LocalDateTime.now().isBefore(this.closeDate)){
            return this.amount;
        }
        Double time = (double) ChronoUnit.YEARS.between(startDate, closeDate);
        return amount + depsitRate.calculateInterestRate("deposit", amount, time);
    }
}
