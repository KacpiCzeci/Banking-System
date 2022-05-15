package com.company;

import com.company.BankProduct.*;
import com.company.BankProduct.Data.BankProductData;
import com.company.InterBankPayment.IBPAagency;
import com.company.InterBankPayment.InterBankPayment;
import com.company.InterestRate.InterestRate;
import com.company.Transaction.ConcreteCommands.*;
import com.company.Transaction.HistoryOfOperations;
import com.company.Transaction.SpecificTransactions.InterBankTransaction;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Bank {
    private final String id;
    private final String name;
    private final BankProductOrganizer bankOrganizer = new BankProductOrganizer();
    private final IBPAagency ibpaAgency;
    private final ArrayList<User> users = new ArrayList<>();
    private final HistoryOfOperations historyOfOperations = new HistoryOfOperations();
    private final BankProductFactory bankProductFactory = new BankProductFactory();

    public Bank(String id, String name, IBPAagency ibpaAgency){
        this.id = id;
        this.name = name;
        this.ibpaAgency = ibpaAgency;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void addToHistory(TransactionCommand transaction){
        this.historyOfOperations.addOperation(transaction);
    }

    public BankProduct createBankProduct(BankProductType type, BankProductData bankProductData){
        BankProduct bankProduct = bankProductFactory.create(type, bankProductData);
        this.bankOrganizer.addBankProduct(bankProduct);
        return bankProduct;
    }

    public void deleteBankProduct(String id, BankProductType type){
        bankOrganizer.deleteProduct(id, type);
    }

    public void createUser(String id){
        users.add(new User(id, this));
    }

    public void deleteUser(String id){
        users.stream().filter(u -> u.getId().equals(id)).findFirst().ifPresent(user -> user.setStatus(UserStatus.DELETED));
    }

    public void makeInterBankPayments(InterBankPayment interBankPayment){
        this.ibpaAgency.addNewInterBankPayments(interBankPayment);
    }

    public void takeInterbankPayment(InterBankTransaction transaction){
        Account account = (Account) this.bankOrganizer.returnProduct(transaction.getReceiverID(), BankProductType.ACCOUNT);
        if(account != null && account.getStatus().equals(BankProductStatus.ACTIVE)){
            account.takePayment(transaction);
        }
    }

    public void makePayment(BankProduct from, BankProduct to, BigDecimal amount){
        TransactionCommand transactionCommand = new PaymentCommand(TransactionType.PAYMENT, from, to, amount);
        from.doTransaction(transactionCommand);
        from.addOperationToHistory(transactionCommand);
        to.addOperationToHistory(transactionCommand);
        this.addToHistory(transactionCommand);
    }

    public void makeWithdrawal(BankProduct from, BigDecimal amount){
        TransactionCommand transactionCommand = new WithdrawalCommand(TransactionType.WITHDRAWAL, from, amount);
        from.doTransaction(transactionCommand);
        from.addOperationToHistory(transactionCommand);
        this.addToHistory(transactionCommand);
    }

    public void makeIncome(BankProduct to, BigDecimal amount){
        TransactionCommand transactionCommand = new IncomeCommand(TransactionType.RECEIVE, to, amount);
        to.doTransaction(transactionCommand);
        to.addOperationToHistory(transactionCommand);
        this.addToHistory(transactionCommand);
    }

    public void changeInterestRate(BankProduct from, InterestRate interestRate){
        TransactionCommand transactionCommand = new InterestRateChangeCommand(TransactionType.CHANGEOFINTERESTRATE, from, interestRate);
        from.doTransaction(transactionCommand);
        from.addOperationToHistory(transactionCommand);
        this.addToHistory(transactionCommand);
    }

    public void closeDeposit(Account account, String id){
        TransactionCommand transactionCommand = new ClosingDepositCommand(TransactionType.CLOSEDEPOSIT, account, id);
        account.doTransaction(transactionCommand);
        account.addOperationToHistory(transactionCommand);
        Deposit deposit = account.getDeposit(id);
        if(deposit != null){
            deposit.addOperationToHistory(transactionCommand);
        }
        this.addToHistory(transactionCommand);
    }

    public void openDeposit(Account account, String id, Integer time){
        TransactionCommand transactionCommand = new OpenDepositCommand(TransactionType.OPENDEPOSIT, account, id, time);
        account.doTransaction(transactionCommand);
        account.addOperationToHistory(transactionCommand);
        Deposit deposit = account.getDeposit(id);
        if(deposit != null){
            deposit.addOperationToHistory(transactionCommand);
        }
        this.addToHistory(transactionCommand);
    }

    public void deposeMoneyToDeposit(Account account, String id, BigDecimal amount){
        TransactionCommand transactionCommand = new DeposeCommand(TransactionType.DEPOSE, account, id, amount);
        account.doTransaction(transactionCommand);
        account.addOperationToHistory(transactionCommand);
        Deposit deposit = account.getDeposit(id);
        if(deposit != null){
            deposit.addOperationToHistory(transactionCommand);
        }
        this.addToHistory(transactionCommand);
    }

    public void takeMoneyFromLoan(Account account, String id, BigDecimal amount){
        TransactionCommand transactionCommand = new TakeLoanCommand(TransactionType.DEPOSE, account, id, amount);
        account.doTransaction(transactionCommand);
        account.addOperationToHistory(transactionCommand);
        Deposit deposit = account.getDeposit(id);
        Loan loan = account.getLoan(id);
        if(loan != null){
            loan.addOperationToHistory(transactionCommand);
        }
        this.addToHistory(transactionCommand);
    }

    public void closeLoan(Account account, String id){
        TransactionCommand transactionCommand = new ClosingDepositCommand(TransactionType.CLOSELOAN, account, id);
        account.doTransaction(transactionCommand);
        account.addOperationToHistory(transactionCommand);
        Loan loan = account.getLoan(id);
        if(loan != null){
            loan.addOperationToHistory(transactionCommand);
        }
        this.addToHistory(transactionCommand);
    }

    public void openLoan(Account account, String id, Integer time){
        TransactionCommand transactionCommand = new OpenDepositCommand(TransactionType.OPENLOAN, account, id, time);
        account.doTransaction(transactionCommand);
        account.addOperationToHistory(transactionCommand);
        Loan loan = account.getLoan(id);
        if(loan != null){
            loan.addOperationToHistory(transactionCommand);
        }
        this.addToHistory(transactionCommand);
    }

    public void takeDebit(DebitAccount debitAccount, BigDecimal amount){
        TransactionCommand transactionCommand = new DebitCommand(TransactionType.DEBIT, debitAccount, amount);
        debitAccount.doTransaction(transactionCommand);
        debitAccount.addOperationToHistory(transactionCommand);
        this.addToHistory(transactionCommand);
    }
}
