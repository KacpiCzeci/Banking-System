package com.company;

import com.company.BankProduct.BankProductStatus;
import com.company.BankProduct.Loan;
import com.company.InterestRate.InterestRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class LoanTest {

    private Loan loanGLOBAL;
    private LocalDateTime localDateTimeCONST= LocalDateTime.now().plusYears(1);

    @Mock Bank bankMOCK;
    @Mock User userMOCK;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        loanGLOBAL =new Loan("0",bankMOCK,userMOCK,localDateTimeCONST);
//        when(interestRateMOCK.calculateInterestRate(new BigDecimal("0.03"), new BigDecimal(10000.00d), new BigDecimal(12))).thenReturn(new BigDecimal(3300.00d));
    }
    @Test
    public void TestCreatingLoan(){
        //given
        Loan loanLOCAL=null;
        //when
        loanLOCAL = new Loan("0", bankMOCK, userMOCK, localDateTimeCONST);
        //then
//        assertTrue(loanLOCAL.getClass().isInstance(loanGLOBAL));
        assertTrue(loanLOCAL!=null);
    }
    @Test
    public void TestReturnValueOfTakingLoanFunction(){
        //given
        BigDecimal expectedReturn = new BigDecimal(10000.00d);
        //when
        BigDecimal acquiredReturn = loanGLOBAL.takeLoan(new BigDecimal(10000.00d));
        //then
        assertEquals(expectedReturn,acquiredReturn);
    }
    @Test
    public void TestChangeOfBalanceInTakingLoan(){
        //given
        BigDecimal expectedBalance = new BigDecimal(10000.00d).setScale(2);
        //when
        loanGLOBAL.takeLoan(new BigDecimal(10000.00d));
        //then
        assertEquals(expectedBalance, loanGLOBAL.getBalance());
    }
    @Test
    public void TestingIfLoanGetStatusClosedAfterPaidIt(){
        //given
        loanGLOBAL.takeLoan(new BigDecimal(10000.00d));
        //when
        loanGLOBAL.payLoan();
        //then
        assertEquals(BankProductStatus.CLOSED,loanGLOBAL.getStatus());
    }

    @Test
    public void TestingCalculateInterestRateInPayLoan(){
        //given
        BigDecimal expectedCalculateInterestRate= new BigDecimal(3300.0000d).setScale(4);
        loanGLOBAL.takeLoan(new BigDecimal(10000.00d));
        //when
        BigDecimal acquiredPayedLoanInterestRate=loanGLOBAL.payLoan().subtract(new BigDecimal(10000.00d));
        //then
        assertEquals(expectedCalculateInterestRate,acquiredPayedLoanInterestRate);
    }

    @Test
    public void TestWithdrawMoneyInLoan(){
        //given
         BigDecimal expectedReturn=new BigDecimal(1000.00d);
        //when
        BigDecimal acquiredReturn = loanGLOBAL.withdrawMoney(new BigDecimal(1000.00d));
        //then
        assertEquals(expectedReturn,acquiredReturn);
    }
}