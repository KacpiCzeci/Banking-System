package com.company;

import com.company.BankProduct.Account;
import com.company.BankProduct.BankProductType;
import com.company.InterestRate.InterestRate;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.InterestRateChangeCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class InterestRateChangeCommandTest {
    public InterestRateChangeCommand interestRateChangeCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;
    @Mock
    InterestRate _InterestRate;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        interestRateChangeCommand = new InterestRateChangeCommand(TransactionType.INTERESTRATECALCULATION, _Account1, _InterestRate);

        when(_Account1.getType()).thenReturn(BankProductType.ACCOUNT);
    }

    @Test
    public void executeTest(){
        interestRateChangeCommand.execute();
        verify(_Account1, times(1)).changeInterestRate(_InterestRate);
    }

    @Test
    public void createDescriptionTest(){
        interestRateChangeCommand.execute();
        assertEquals(interestRateChangeCommand.getDescription(), "Changed interest rates.");
    }

    @Test
    public void acceptVisitorTest(){
        interestRateChangeCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitTransaction(interestRateChangeCommand);
    }

    @AfterEach
    public void clearData(){
        interestRateChangeCommand = null;
    }
}
