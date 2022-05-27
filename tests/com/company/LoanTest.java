package com.company;

import com.company.BankProduct.Account;
import com.company.BankProduct.Loan;
import com.company.InterestRate.InterestRate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

class LoanTest {

    public Loan loan;

    @Mock public InterestRate interestRate;
    @Mock public Account account;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

//        when(interestRate.calculateInterestRate("loan", 1345.76, 7.0)).thenReturn(942.03);
    }

//    @Test
//    public void getLoan() {
//        loan = new Loan(123L, 1345.76, LocalDateTime.now().minusMonths(3), LocalDateTime.now().plusMonths(4), interestRate);
//        assertEquals(2287.79, loan.getLoan(), 0.001);
//        verify(interestRate, times(1)).calculateInterestRate("loan", 1345.76, 7.0);
//    }

    @Test
    public void payLoan() {
//        loan = new Loan(123L, 1345.76, LocalDateTime.now().minusMonths(3), LocalDateTime.now().plusMonths(4), interestRate);
//        loan.payLoan();
//        assertEquals(0.00, loan.getAmount(), 0.001);
    }
}