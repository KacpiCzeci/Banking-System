package com.company;

import com.company.BankProduct.Account;
import com.company.BankProduct.Loan;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.OpenLoanCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OpenLoanCommandTest {
    public OpenLoanCommand openLoanCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;
    @Mock
    Loan _Loan;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        openLoanCommand = new OpenLoanCommand(TransactionType.OPENLOAN, _Account1, "222444888", 14);

        when(_Account1.getId()).thenReturn("123456789");
        when(_Account1.getLoan("222444888")).thenReturn(_Loan);
        when(_Loan.getId()).thenReturn("222444888");
    }

    @Test
    public void executeTest(){
        openLoanCommand.execute();
        verify(_Account1, times(1)).openLoan("222444888", 14);
    }

    @Test
    public void createDescriptionTest(){
        openLoanCommand.execute();
        assertEquals(openLoanCommand.getDescription(), "Open Loan 222444888 to account 123456789.");
    }

    @Test
    public void acceptVisitorTest(){
        openLoanCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visit(openLoanCommand);
    }

    @AfterEach
    public void clearData(){
        openLoanCommand = null;
    }
}
