package com.company;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.FailureCommand;
import com.company.Transaction.TransactionCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class FailureCommandTest {
    public FailureCommand failureCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;
    @Mock
    TransactionCommand _TransactionCommand;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        failureCommand = new FailureCommand(TransactionType.FAILURE, _TransactionCommand, _Account1);

        when(_Account1.getId()).thenReturn("123456789");
        when(_TransactionCommand.getType()).thenReturn(TransactionType.DEPOSE);
    }

    @Test
    public void executeTest(){
        failureCommand.execute();
        verify(_Account1, times(1)).handleFailure(_TransactionCommand);
    }

    @Test
    public void createDescriptionTest(){
        failureCommand.execute();
        assertEquals(failureCommand.getDescription(), "Failure of transaction: DEPOSE.");
    }

    @Test
    public void acceptVisitorTest(){
        failureCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitTransaction(failureCommand);
    }

    @AfterEach
    public void clearData(){
        failureCommand = null;
    }
}
