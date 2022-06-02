package com.company;

import com.company.BankProduct.Account;
import com.company.BankProduct.DebitAccount;
import com.company.BankProduct.Deposit;
import com.company.BankProduct.Loan;
import com.company.Card.Card;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.InterBankCommand;
import com.company.Transaction.TransactionCommand;
import com.company.TransferVerification.TransferVerification;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DebitAccountTest {

    public DebitAccount debitAccount;
    @Mock Bank bankMOCK;
    @Mock Account accountMOCK;
    @Mock User userMOCK;
    @Mock TransferVerification transferVerificationMOCK;
    @Mock TransactionCommand transactionCommandMOCK;
    @Mock ReportVisitor reportVisitorMOCK;
    @Mock Deposit depositMOCK;
    @Mock Loan loanMOCK;
    @Mock Card cardMOCK;
    @Mock InterBankCommand interBankCommandMOCK;

//    @BeforeAll
//    public void setUpAll(){
//    }

    @BeforeEach
    public void setUpEach(){
        MockitoAnnotations.initMocks(this);

        debitAccount=new DebitAccount("123456789",bankMOCK, userMOCK,accountMOCK);

    }

    @Test
    public void checkDebitAccountGetBalanceMethod() {
        debitAccount.getBalance();
        verify(accountMOCK,times(1)).getBalance();
//        assertEquals(debitAccount.getBalance(), amount);
    }

    @Test
    public void checkIfDebitAccountGetBalanceMethodGiveValueFromAccount() {
        //given
        when(accountMOCK.getBalance()).thenReturn(new BigDecimal("1000.00"));
        //when
        BigDecimal accountBalance=debitAccount.getBalance();
        //then
        assertEquals(new BigDecimal("1000.00"),accountBalance);
    }
    @Test
    public void checkIfDebitLimitIsSetOkey(){
        debitAccount.setDebitLimit(new BigDecimal("10000.00"));
        assertEquals(new BigDecimal("10000.00"),debitAccount.getDebitLimit());
    }

    @Test
    public void withdrawMoneyLessThenBalance(){
        //given
        when(accountMOCK.getBalance()).thenReturn(new BigDecimal("1000.00"));
        //when
        debitAccount.withdrawMoney(new BigDecimal("10.00"));
        //then
        verify(accountMOCK,times(1)).withdrawMoney(new BigDecimal("10.00"));
    }
    @Test
    public void withdrawMoneyMoreThenBalanceCheckDebit(){
        //given
        when(accountMOCK.getBalance()).thenReturn(new BigDecimal("1000.00"));
        when(accountMOCK.withdrawMoney(any(BigDecimal.class))).thenReturn(new BigDecimal("1000.00"));
        debitAccount.setDebitLimit(new BigDecimal("2000.00"));
        //when
        debitAccount.withdrawMoney(new BigDecimal("1500.00"));
        //then
        assertEquals(new BigDecimal("500.00"),debitAccount.getDebit());
    }
    @Test
    public void withdrawMoneyMoreThenBalanceCheckAccountCalling(){
        //given
        when(accountMOCK.getBalance()).thenReturn(new BigDecimal("1000.00"));
        when(accountMOCK.withdrawMoney(any(BigDecimal.class))).thenReturn(new BigDecimal("1000.00"));
        debitAccount.setDebitLimit(new BigDecimal("2000.00"));
        //when
        debitAccount.withdrawMoney(new BigDecimal("1500.00"));
        //then
        verify(accountMOCK,times(1)).withdrawMoney(new BigDecimal("1000.00"));
    }
    @Test
    public void withdrawMoneyMoreThenBalanceAccount(){
        //given
        when(accountMOCK.getBalance()).thenReturn(new BigDecimal("1000.00"));
        when(accountMOCK.withdrawMoney(any(BigDecimal.class))).thenReturn(new BigDecimal("1000.00"));
        debitAccount.setDebitLimit(new BigDecimal("2000.00"));
        //when
        BigDecimal ammountMoney  = debitAccount.withdrawMoney(new BigDecimal("1500.00"));
        //then
        assertEquals(new BigDecimal("1500.00"),ammountMoney);
    }
    @Test
    public void withdrawMoneyWrongValue(){
        //given
        when(accountMOCK.getBalance()).thenReturn(new BigDecimal("1000.00"));
        when(accountMOCK.withdrawMoney(any(BigDecimal.class))).thenReturn(new BigDecimal("1000.00"));
        debitAccount.setDebitLimit(new BigDecimal("2000.00"));
        //when
        BigDecimal ammountMoney  = debitAccount.withdrawMoney(new BigDecimal("-1500.00"));
        //then
        assertEquals(new BigDecimal("0.00"),ammountMoney);
        verify(accountMOCK,times(0)).withdrawMoney(new BigDecimal("1000.00"));

    }

    @Test
    public void receiveMoneyPayOffThePartOfDebit(){
        //given
        when(accountMOCK.getBalance()).thenReturn(new BigDecimal("1000.00"));
        when(accountMOCK.withdrawMoney(any(BigDecimal.class))).thenReturn(new BigDecimal("1000.00"));
        debitAccount.setDebitLimit(new BigDecimal("2000.00"));
        BigDecimal ammountMoney  = debitAccount.withdrawMoney(new BigDecimal("1500.00"));
        //when
        debitAccount.receiveMoney(new BigDecimal("200.00"));
        //then
        assertEquals(new BigDecimal("300.00"),debitAccount.getDebit());
    }

    @Test
    public void receiveMoneyPayOffTheOfDebit(){
        //given
        when(accountMOCK.getBalance()).thenReturn(new BigDecimal("1000.00"));
        when(accountMOCK.withdrawMoney(any(BigDecimal.class))).thenReturn(new BigDecimal("1000.00"));
        debitAccount.setDebitLimit(new BigDecimal("2000.00"));
        BigDecimal ammountMoney  = debitAccount.withdrawMoney(new BigDecimal("1500.00"));
        //when
        debitAccount.receiveMoney(new BigDecimal("500.00"));
        //then
        assertEquals(new BigDecimal("0.00"),debitAccount.getDebit());
    }
    @Test
    public void receiveMoneyPayOffTheOfDebitAddMoreMoney(){
        //given
        when(accountMOCK.getBalance()).thenReturn(new BigDecimal("1000.00"));
        when(accountMOCK.withdrawMoney(any(BigDecimal.class))).thenReturn(new BigDecimal("1000.00"));
        debitAccount.setDebitLimit(new BigDecimal("2000.00"));
        debitAccount.withdrawMoney(new BigDecimal("1500.00"));
        //when
        debitAccount.receiveMoney(new BigDecimal("700.00"));
        //then
        verify(accountMOCK,times(1)).receiveMoney(new BigDecimal("200.00"));
    }
    @Test
    public void receiveMoneyWrongValue(){
        //given
        when(accountMOCK.getBalance()).thenReturn(new BigDecimal("1000.00"));
        when(accountMOCK.withdrawMoney(any(BigDecimal.class))).thenReturn(new BigDecimal("1000.00"));
        debitAccount.setDebitLimit(new BigDecimal("2000.00"));
        debitAccount.withdrawMoney(new BigDecimal("1500.00"));
        //when
        debitAccount.receiveMoney(new BigDecimal("-700.00"));
        //then
        verify(accountMOCK,times(0)).receiveMoney(new BigDecimal("200.00"));
        assertEquals(new BigDecimal("500.00"),debitAccount.getDebit());
    }
    @Test
    public void doTransactionTest(){
        //when
        debitAccount.doTransaction(transactionCommandMOCK);
        //then
        verify(transactionCommandMOCK, times(1)).execute();
    }
    @Test
    public void handleFailureTestRedirectToAccount(){
        //when
        debitAccount.handleFailure(interBankCommandMOCK);
        //then
        verify(accountMOCK, times(1)).handleFailure(interBankCommandMOCK);
    }

    @Test
    public void acceptVisitorTest(){
        //when
        debitAccount.acceptVisitor(reportVisitorMOCK);
        //then
        verify(reportVisitorMOCK, times(1)).visit(debitAccount);
    }
    @Test
    public void openDepositTestRedirectToAccount(){
        //when
        debitAccount.openDeposit("12", 6);
        //then
        verify(accountMOCK, times(1)).openDeposit("12",6);
    }

    @Test
    public void openLoanTestRedirectToAccount(){
        //when
        debitAccount.openLoan("12", 6);
        //then
        verify(accountMOCK, times(1)).openLoan("12",6);
    }
    @Test
    public void deposeMoneyToDepositTestToAccount(){
        //when
        debitAccount.deposeMoneyToDeposit("12", new BigDecimal("123456.00"));
        //then
        verify(accountMOCK, times(1)).deposeMoneyToDeposit("12", new BigDecimal("123456.00"));
    }
    @Test
    public void takeMoneyFromLoanTestRedirectToAccount(){
        //when
        debitAccount.takeMoneyFromLoan("12", new BigDecimal("123456.00"));
        //then
        verify(accountMOCK, times(1)).takeMoneyFromLoan("12", new BigDecimal("123456.00"));
    }
    @Test
    public void closeDepositTestRedirectToAccount(){
        //when
        debitAccount.closeDeposit("123456789");
        //then
        verify(accountMOCK, times(1)).closeDeposit("123456789");

    }
    @Test
    public void closeLoanTestRedirectToAccount(){
        //when
        debitAccount.closeLoan("123456789");
        //then
        verify(accountMOCK, times(1)).closeLoan("123456789");
    }
    @Test
    public void addCardTestRedirectToAccount(){
        //when
        debitAccount.addCard(cardMOCK);
        //then
        verify(accountMOCK, times(1)).addCard(cardMOCK);
    }

    @Test
    public void removeCardTestRedirectToAccount(){
        //when
        debitAccount.removeCard(1L);
        //then
        verify(accountMOCK, times(1)).removeCard(1L);
    }
    @Test
    public void useCardPaymentTestRedirectToAccount(){
        //when
        debitAccount.useCardPayment(1L,new BigDecimal("11.00"));
        //then
        verify(accountMOCK, times(1)).useCardPayment(1L,new BigDecimal("11.00"));
    }

    @AfterEach
    public void clearDataEach(){
        debitAccount = null;
    }


}