package com.company;

import com.company.BankProduct.*;
import com.company.BankProduct.Data.BankProductData;
import com.company.InterBankPayment.IBPAagency;
import com.company.InterestRate.InterestRate;
import com.company.Transaction.ConcreteCommands.*;
import com.company.Transaction.HistoryOfOperations;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import com.company.TransferVerification.TransferVerification;
import com.company.TransferVerification.TransferVerificationCreator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Bank {
    private final String id;
    private final String name;
    private final BankProductOrganizer bankOrganizer = new BankProductOrganizer();
    private final IBPAagency ibpaAgency;
    private final ArrayList<User> users = new ArrayList<>();
    private final HistoryOfOperations historyOfOperations = new HistoryOfOperations();
    private final BankProductCreator bankProductCreator = new BankProductCreator();
    private final TransferVerificationCreator transferVerificationCreator = new TransferVerificationCreator(this);

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
        BankProduct bankProduct = bankProductCreator.create(type, bankProductData);
        this.bankOrganizer.addBankProduct(bankProduct);
        return bankProduct;
    }

    public void deleteBankProduct(String id, BankProductType type){
        this.bankOrganizer.deleteProduct(id, type);
    }

    public BankProduct getBankProduct(String id, BankProductType type){
        return this.bankOrganizer.returnProduct(id, type);
    }

    public void createUser(String id){
        users.add(new User(id, this));
    }

    public void deleteUser(String id){
        users.stream().filter(u -> u.getId().equals(id)).findFirst().ifPresent(user -> user.setStatus(UserStatus.DELETED));
    }

    public void makeInterBankPayments(Account account, String idBank, String idAccount, BigDecimal amount){
        InterBankCommand transactionCommand = new InterBankCommand(TransactionType.INTERBANKPAYMENT, account,  this, idBank, idAccount, amount);
        account.doTransaction(transactionCommand);
        account.addOperationToHistory(transactionCommand);
        this.addToHistory(transactionCommand);
        this.ibpaAgency.doInterbankPayment(this, transactionCommand);
    }

    public void takeInterbankPayment(InterBankCommand interBankPayment){
        Account receiver = (Account) this.bankOrganizer.returnProduct(interBankPayment.getReceiverId(), BankProductType.ACCOUNT);
        if(receiver != null && receiver.getStatus().equals(BankProductStatus.ACTIVE)){
            receiver.doTransaction(interBankPayment);
            receiver.addOperationToHistory(interBankPayment);
            this.addToHistory(interBankPayment);
        }
        else{
            FailureCommand failureCommand = new FailureCommand(TransactionType.FAILURE, interBankPayment, interBankPayment.getAccount());
            this.ibpaAgency.doInterbankPayment(this, failureCommand);
        }
    }

    public void takeFailure(FailureCommand failureCommand){
        failureCommand.getReceiver().doTransaction(failureCommand);
        failureCommand.getReceiver().addOperationToHistory(failureCommand);
        this.addToHistory(failureCommand);
    }

    public void makePayment(BankProduct from, BankProduct to, BigDecimal amount){
        TransactionCommand transactionCommand = new PaymentCommand(TransactionType.PAYMENT, from, to, amount);
        TransferVerification transferVerification = this.transferVerificationCreator.create(TransactionType.PAYMENT);
        if(transferVerification.verifyTransaction(transactionCommand)){
            from.doTransaction(transactionCommand);
            from.addOperationToHistory(transactionCommand);
            to.addOperationToHistory(transactionCommand);
            this.addToHistory(transactionCommand);
        }
        else{
            this.takeFailure(new FailureCommand(TransactionType.FAILURE, transactionCommand, from));
        }
    }

    public void makeWithdrawal(BankProduct from, BigDecimal amount){
        TransactionCommand transactionCommand = new WithdrawalCommand(TransactionType.WITHDRAWAL, from, amount);
        TransferVerification transferVerification = this.transferVerificationCreator.create(TransactionType.WITHDRAWAL);
        if(transferVerification.verifyTransaction(transactionCommand)){
            from.doTransaction(transactionCommand);
            from.addOperationToHistory(transactionCommand);
            this.addToHistory(transactionCommand);
        }
        else {
            this.takeFailure(new FailureCommand(TransactionType.FAILURE, transactionCommand, from));
        }
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

    /**
     * Tu coś jest nie tak chyba
     */
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

    public void addCard(Account account, String id, Long number, Integer cvc, LocalDateTime expDate){
        TransactionCommand transactionCommand = new AddCardCommand(TransactionType.ADDCARD, account, id, number, cvc, expDate);
        account.doTransaction(transactionCommand);
        account.addOperationToHistory(transactionCommand);
        this.addToHistory(transactionCommand);
    }

    public void removeCard(Account account, Long number){
        TransactionCommand transactionCommand = new RemoveCard(TransactionType.REMOVECARD, account, number);
        account.doTransaction(transactionCommand);
        account.addOperationToHistory(transactionCommand);
        this.addToHistory(transactionCommand);
    }

    public void useCard(Account account, Long number){
        TransactionCommand transactionCommand = new RemoveCard(TransactionType.USECARD, account, number);
        account.doTransaction(transactionCommand);
        account.addOperationToHistory(transactionCommand);
        this.addToHistory(transactionCommand);
    }
}
