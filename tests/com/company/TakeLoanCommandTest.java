package com.company;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.TakeLoanCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class TakeLoanCommandTest {
    public TakeLoanCommand takeLoanCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        takeLoanCommand = new TakeLoanCommand(TransactionType.TAKELOAN, _Account1, "123456789", new BigDecimal("40.00"));
    }

    @Test
    public void executeTest(){
        takeLoanCommand.execute();
        verify(_Account1, times(1)).takeMoneyFromLoan( "123456789",new BigDecimal("40.00"));
    }

    @Test
    public void createDescriptionTest(){
        takeLoanCommand.execute();
        assertEquals(takeLoanCommand.getDescription(), "Take loan in amount of 40.00.");
    }

    @Test
    public void acceptVisitorTest(){
        takeLoanCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitTransaction(takeLoanCommand);
    }

    @AfterEach
    public void clearData(){
        takeLoanCommand = null;
    }
}
