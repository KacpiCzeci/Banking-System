package com.company;

import com.company.BankProduct.*;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BankProductOrganizerTest {

    public BankProductOrganizer bankProductOrganizer;

    @Mock Account _Account;
    @Mock Deposit _Deposit;
    @Mock Loan _Loan;
    @Mock DebitAccount _DebitAccount;


    @BeforeEach
    public void setUpEach() {
        MockitoAnnotations.initMocks(this);
        bankProductOrganizer = new BankProductOrganizer();

        when(_Account.getType()).thenReturn(BankProductType.ACCOUNT);
        when(_Account.getId()).thenReturn("123456789");
        when(_Deposit.getType()).thenReturn(BankProductType.DEPOSIT);
        when(_Deposit.getId()).thenReturn("123456789");
        when(_DebitAccount.getType()).thenReturn(BankProductType.DEBITACCOUNT);
        when(_DebitAccount.getId()).thenReturn("123456789");
        when(_Loan.getType()).thenReturn(BankProductType.LOAN);
        when(_Loan.getId()).thenReturn("123456789");
    }

    @Test
    public void addBankProductAccountTest(){
        bankProductOrganizer.addBankProduct(_Account);
        verify(_Account, times(1)).getType();
        assertEquals(bankProductOrganizer.getAccounts().size(), 1);
        assertEquals(bankProductOrganizer.getAccounts().get(0), _Account);
    }

    @Test
    public void addBankProductDepositTest(){
        bankProductOrganizer.addBankProduct(_Deposit);
        verify(_Deposit, times(1)).getType();
        assertEquals(bankProductOrganizer.getDeposits().size(), 1);
        assertEquals(bankProductOrganizer.getDeposits().get(0), _Deposit);
    }

    @Test
    public void addBankProductLoanTest(){
        bankProductOrganizer.addBankProduct(_Loan);
        verify(_Loan, times(1)).getType();
        assertEquals(bankProductOrganizer.getLoans().size(), 1);
        assertEquals(bankProductOrganizer.getLoans().get(0), _Loan);
    }

    @Test
    public void addBankProductDebitAccountTest(){
        bankProductOrganizer.addBankProduct(_DebitAccount);
        verify(_DebitAccount, times(1)).getType();
        assertEquals(bankProductOrganizer.getDebitAccounts().size(), 1);
        assertEquals(bankProductOrganizer.getDebitAccounts().get(0), _DebitAccount);
    }

    @Test
    public void returnProductAccountCorrectTest(){
        bankProductOrganizer.addBankProduct(_Account);
        BankProduct acc = bankProductOrganizer.returnProduct("123456789", BankProductType.ACCOUNT);
        assertEquals(acc.getId(), "123456789");
        assertEquals(acc.getType(), BankProductType.ACCOUNT);
    }

    @Test
    public void returnProductAccountWrongIdTest(){
        bankProductOrganizer.addBankProduct(_Account);
        BankProduct acc = bankProductOrganizer.returnProduct("3242342", BankProductType.ACCOUNT);
        assertNull(acc);
    }

    @Test
    public void returnProductAccountWrongIdTypeTest(){
        bankProductOrganizer.addBankProduct(_Account);
        BankProduct acc = bankProductOrganizer.returnProduct("3242342", BankProductType.DEPOSIT);
        assertNull(acc);
    }

    @Test
    public void returnProductAccountWrongTypeTest(){
        bankProductOrganizer.addBankProduct(_Account);
        BankProduct acc = bankProductOrganizer.returnProduct("123456789", BankProductType.DEBITACCOUNT);
        assertNull(acc);
    }

    @Test
    public void returnProductDebitCorrectTest(){
        bankProductOrganizer.addBankProduct(_DebitAccount);
        BankProduct deb = bankProductOrganizer.returnProduct("123456789", BankProductType.DEBITACCOUNT);
        assertEquals(deb.getId(), "123456789");
        assertEquals(deb.getType(), BankProductType.DEBITACCOUNT);
    }

    @Test
    public void returnProductDebitWrongIdTest(){
        bankProductOrganizer.addBankProduct(_DebitAccount);
        BankProduct deb = bankProductOrganizer.returnProduct("3242342", BankProductType.DEBITACCOUNT);
        assertNull(deb);
    }

    @Test
    public void returnProductDebitWrongIdTypeTest(){
        bankProductOrganizer.addBankProduct(_DebitAccount);
        BankProduct deb = bankProductOrganizer.returnProduct("3242342", BankProductType.DEPOSIT);
        assertNull(deb);
    }

    @Test
    public void returnProductDebitWrongTypeTest(){
        bankProductOrganizer.addBankProduct(_DebitAccount);
        BankProduct deb = bankProductOrganizer.returnProduct("123456789", BankProductType.ACCOUNT);
        assertNull(deb);
    }

    @Test
    public void returnProductDepositCorrectTest(){
        bankProductOrganizer.addBankProduct(_Deposit);
        BankProduct dep = bankProductOrganizer.returnProduct("123456789", BankProductType.DEPOSIT);
        assertEquals(dep.getId(), "123456789");
        assertEquals(dep.getType(), BankProductType.DEPOSIT);
    }

    @Test
    public void returnProductDepositWrongIdTest(){
        bankProductOrganizer.addBankProduct(_Deposit);
        BankProduct dep = bankProductOrganizer.returnProduct("3242342", BankProductType.DEPOSIT);
        assertNull(dep);
    }

    @Test
    public void returnProductDepositWrongIdTypeTest(){
        bankProductOrganizer.addBankProduct(_Deposit);
        BankProduct dep = bankProductOrganizer.returnProduct("3242342", BankProductType.LOAN);
        assertNull(dep);
    }

    @Test
    public void returnProductDepositWrongTypeTest(){
        bankProductOrganizer.addBankProduct(_Deposit);
        BankProduct dep = bankProductOrganizer.returnProduct("123456789", BankProductType.DEBITACCOUNT);
        assertNull(dep);
    }

    @Test
    public void returnProductLoanCorrectTest(){
        bankProductOrganizer.addBankProduct(_Loan);
        BankProduct lo = bankProductOrganizer.returnProduct("123456789", BankProductType.LOAN);
        assertEquals(lo.getId(), "123456789");
        assertEquals(lo.getType(), BankProductType.LOAN);
    }

    @Test
    public void returnProductLoanWrongIdTest(){
        bankProductOrganizer.addBankProduct(_Loan);
        BankProduct lo = bankProductOrganizer.returnProduct("3242342", BankProductType.LOAN);
        assertNull(lo);
    }

    @Test
    public void returnProductLoanWrongIdTypeTest(){
        bankProductOrganizer.addBankProduct(_Loan);
        BankProduct lo = bankProductOrganizer.returnProduct("3242342", BankProductType.DEPOSIT);
        assertNull(lo);
    }

    @Test
    public void returnProductLoanWrongTypeTest(){
        bankProductOrganizer.addBankProduct(_Loan);
        BankProduct lo = bankProductOrganizer.returnProduct("123456789", BankProductType.DEBITACCOUNT);
        assertNull(lo);
    }

    @Test
    public void deleteProductAccountTest(){
        bankProductOrganizer.addBankProduct(_Account);
        assertEquals(bankProductOrganizer.getAccounts().size(), 1);

        bankProductOrganizer.deleteProduct("1324324", BankProductType.ACCOUNT);
        verify(_Account, times(0)).setStatus(BankProductStatus.CLOSED);
        bankProductOrganizer.deleteProduct("1324324", BankProductType.DEPOSIT);
        verify(_Account, times(0)).setStatus(BankProductStatus.CLOSED);
        bankProductOrganizer.deleteProduct("123456789", BankProductType.LOAN);
        verify(_Account, times(0)).setStatus(BankProductStatus.CLOSED);
        bankProductOrganizer.deleteProduct("123456789", BankProductType.ACCOUNT);
        verify(_Account, times(1)).setStatus(BankProductStatus.CLOSED);

        assertEquals(bankProductOrganizer.getAccounts().size(), 1);
    }
    
    @AfterEach
    public void clearDataEach(){
        bankProductOrganizer = null;
    }
}
