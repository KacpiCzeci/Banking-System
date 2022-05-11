package com.company;

import com.company.BankProduct.*;

import java.util.ArrayList;

public class BankProductOrganizer {
    private final ArrayList<Account> accounts = new ArrayList<>();
    private final ArrayList<DebitAccount> debitAccounts = new ArrayList<>();
    private final ArrayList<Deposit> deposits = new ArrayList<>();
    private final ArrayList<Loan> loans = new ArrayList<>();

    public BankProductOrganizer(){
    }

    public void addBankProduct(BankProduct product){
        switch (product.getType()){
            case ACCOUNT:
                accounts.add((Account) product);
                break;
            case DEBITACCOUNT:
                debitAccounts.add((DebitAccount) product);
                break;
            case DEPOSIT:
                deposits.add((Deposit) product);
                break;
            case LOAN:
                loans.add((Loan) product);
                break;
            default:
                break;
        }
    }

    public BankProduct returnProduct(String id, BankProductType type){
        switch (type){
            case ACCOUNT:
                return accounts.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
            case DEBITACCOUNT:
                return debitAccounts.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
            case DEPOSIT:
                return deposits.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
            case LOAN:
                return loans.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
            default:
                return null;
        }
    }

    public void deleteProduct(String id, BankProductType type){
        switch (type){
            case ACCOUNT:
                Account account = accounts.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
                if (account != null){
                    account.setStatus(BankProductStatus.CLOSED);
                }
                break;
            case DEBITACCOUNT:
                DebitAccount debitAccount = debitAccounts.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
                if (debitAccount != null){
                    debitAccount.setStatus(BankProductStatus.CLOSED);
                }
                break;
            case DEPOSIT:
                Deposit deposit = deposits.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
                if (deposit != null){
                    deposit.setStatus(BankProductStatus.CLOSED);
                }
                break;
            case LOAN:
                Loan loan = loans.stream().filter(product -> product.getId().equals(id)).findFirst().orElse(null);
                if (loan != null){
                    loan.setStatus(BankProductStatus.CLOSED);
                }
                break;
            default:
                break;
        }
    }

}
