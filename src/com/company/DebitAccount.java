package com.company;

public class DebitAccount extends Account {
    private Double debitLimit;
    private Double debit;

    private DebitAccount(Bank bank, String owner, TransferVerification trVr){
        super(bank, owner, trVr);
        this.debitLimit = 0.0;
        this.debit = 0.0;
    }

    public Double getDebitLimit() {
        return debitLimit;
    }

    public void setDebitLimit(Double debit) {
        this.debitLimit = debit;
    }

    public Double getDebit() {
        return debit;
    }

    @Override
    public void addMoney(Double amount){
        if(this.debit > 0.00){
            if(this.debit < amount){
                this.totalMoney += (amount - this.debit);
                this.debit = 0.00;
            }
            if(this.debit > amount){
                this.debit -= amount;
            }
        }
    }

    public Boolean takeDebit(Double amount){
        if(amount + this.debit > this.debitLimit){
            //System.out.println("You cannot take more debit, it will be over limit!");
            //System.out.println("The limit is: " + this.debitLimit.toString());
            //System.out.println(("The actual debit is: " + this.debit.toString()));
            return false;
        }
        else{
            this.debit += this.debit + amount;
            return true;
        }
    }
}
