package com.company;

import com.company.BankProduct.Account;
import com.company.BankProduct.BankProductStatus;
import com.company.BankProduct.Deposit;
import com.company.BankProduct.Loan;
import com.company.InterestRate.InterestRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class DepositTest {

    private Deposit depositGLOBAL;
    private LocalDateTime localDateTimeCONST= LocalDateTime.now().plusYears(1);
    @Mock Bank bankMOCK;
    @Mock User userMOCK;
    @Mock InterestRate interestRateMOCK;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        depositGLOBAL=new Deposit("0",bankMOCK,userMOCK,localDateTimeCONST);
//        when(interestRate.calculateInterestRate("deposit", 1000.0, 1.0)).thenReturn(1071.03);
    }
    @Test
    public void TestCreatingDeposit(){
        //given
        Deposit depositLOCAL=null;
        //when
        depositLOCAL = new Deposit("0", bankMOCK, userMOCK, localDateTimeCONST);
        //then
//        assertTrue(loanLOCAL.getClass().isInstance(loanGLOBAL));
        assertTrue(depositLOCAL!=null);

    }
    @Test
    public void CheckBalanceOfDeposit(){
        //given
        BigDecimal moneyToDeposit=new BigDecimal(10.0d).setScale(2);
        //when
        depositGLOBAL.depositMoney(moneyToDeposit);
        //then
        assertEquals(depositGLOBAL.getBalance(),moneyToDeposit);
    }
    @Test
    public void CheckWithdrawMoneyInDeposit(){
        //given
        BigDecimal moneyToWithdraw = new BigDecimal(100.0).setScale(2);
        //when
        BigDecimal withdrawMoney=depositGLOBAL.withdrawMoney(moneyToWithdraw);
        //then
        assertEquals(moneyToWithdraw,withdrawMoney);
    }
    @Test
    public void CheckCloseDepositBeforeTime(){
        //given
        BigDecimal moneyToDeposit=new BigDecimal(10.0d).setScale(2);
        depositGLOBAL.depositMoney(moneyToDeposit);
        //when
        BigDecimal moneyFromDeposit=depositGLOBAL.closeDeposit();
        //then
        assertEquals(moneyToDeposit,moneyFromDeposit);
    }
    @Test
    public void CheckCloseDepositAfterTimeGetInterestRateLinear(){
        //given
        depositGLOBAL=new Deposit("0",bankMOCK,userMOCK,localDateTimeCONST.minusYears(2));
        BigDecimal moneyToDeposit=new BigDecimal(100.0d).setScale(2);
        BigDecimal interestRateFromOneYearLinear=moneyToDeposit.multiply(new BigDecimal("12").multiply(new BigDecimal("0.03")));
        depositGLOBAL.depositMoney(moneyToDeposit);
        //when
        BigDecimal moneyFromDeposit=depositGLOBAL.closeDeposit();
        BigDecimal interestRateFromDeposit=moneyFromDeposit.subtract(moneyToDeposit);
        //then
        assertEquals(interestRateFromOneYearLinear,interestRateFromDeposit);
    }
    @Test
    public void TestingIfDepositGetStatusClosedAfterClosedIt(){
        //give
        //when
        depositGLOBAL.closeDeposit();
        //then
        assertEquals(BankProductStatus.CLOSED,depositGLOBAL.getStatus());
    }
}