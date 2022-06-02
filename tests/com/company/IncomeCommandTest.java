package com.company;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.IncomeCommand;
import com.company.Transaction.ConcreteCommands.WithdrawalCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class IncomeCommandTest {
    public IncomeCommand incomeCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        incomeCommand = new IncomeCommand(TransactionType.RECEIVE, _Account1, new BigDecimal("40.00"));

        when(_Account1.getId()).thenReturn("123456789");
    }

    @Test
    public void executeTest(){
        incomeCommand.execute();
        verify(_Account1, times(1)).receiveMoney(new BigDecimal("40.00"));
    }

    @Test
    public void createDescriptionTest(){
        incomeCommand.execute();
        assertEquals(incomeCommand.getDescription(), "Income in amount of 40.00.");
    }

    @Test
    public void acceptVisitorTest(){
        incomeCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitTransaction(incomeCommand);
    }

    @AfterEach
    public void clearData(){
        incomeCommand = null;
    }
}
