package com.company;

import com.company.BankProduct.Account;
import com.company.BankProduct.Loan;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.CloseLoanCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CloseLoanCommandTest {
    public CloseLoanCommand closeLoanCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;
    @Mock
    Loan _Loan;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        closeLoanCommand = new CloseLoanCommand(TransactionType.CLOSELOAN, _Account1, "123456789");

        when(_Account1.getId()).thenReturn("123456789");
        when(_Account1.getLoan("123456789")).thenReturn(_Loan);
        when(_Loan.getId()).thenReturn("123456789");
    }

    @Test
    public void executeTest(){
        closeLoanCommand.execute();
        verify(_Account1, times(1)).closeLoan("123456789");
    }

    @Test
    public void createDescriptionTest(){
        closeLoanCommand.execute();
        assertEquals(closeLoanCommand.getDescription(), "Closed Loan 123456789 of account 123456789.");
    }

    @Test
    public void acceptVisitorTest(){
        closeLoanCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visit(closeLoanCommand);
    }

    @AfterEach
    public void clearData(){
        closeLoanCommand = null;
    }
}
