package com.company;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.DeposeCommand;
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

public class DeposeCommandTest {
    public DeposeCommand deposeCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        deposeCommand = new DeposeCommand(TransactionType.DEPOSE, _Account1, "123456789", new BigDecimal("40.00"));

        when(_Account1.getId()).thenReturn("123456789");
    }

    @Test
    public void executeTest(){
        deposeCommand.execute();
        verify(_Account1, times(1)).deposeMoneyToDeposit("123456789", new BigDecimal("40.00"));
    }

    @Test
    public void createDescriptionTest(){
        deposeCommand.execute();
        assertEquals(deposeCommand.getDescription(), "Deposed money to 123456789 in amount of 40.00.");
    }

    @Test
    public void acceptVisitorTest(){
        deposeCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitTransaction(deposeCommand);
    }

    @AfterEach
    public void clearData(){
        deposeCommand = null;
    }
}
