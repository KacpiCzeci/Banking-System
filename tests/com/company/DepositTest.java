package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DepositTest {

    public Deposit deposit;
    @Mock public InterestRate interestRate;
    @Mock public Account account;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);

        when(interestRate.calculateInterestRate("deposit", 1000.0, 1.0)).thenReturn(1071.03);
    }

    @Test
    public void WithDrawMoneyFromDeposit(){
        deposit=new Deposit(123L,1000.0, LocalDateTime.now().minusMonths(3), LocalDateTime.now().minusMonths(2), interestRate);
        assertEquals(1071.03,deposit.withdrawMoney());
    }
}