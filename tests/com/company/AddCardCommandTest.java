package com.company;

import com.company.BankProduct.Account;
import com.company.Card.Card;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.AddCardCommand;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AddCardCommandTest {
    public AddCardCommand addCardCommand;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;
    @Mock
    Card _Card;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        addCardCommand = new AddCardCommand(TransactionType.ADDCARD, _Account1, _Card);

        when(_Account1.getId()).thenReturn("123456789");
        when(_Card.getNumber()).thenReturn(32964932432323L);
    }

    @Test
    public void executeTest(){
        addCardCommand.execute();
        verify(_Account1, times(1)).addCard(_Card);
    }

    @Test
    public void createDescriptionTest(){
        addCardCommand.execute();
        assertEquals(addCardCommand.getDescription(), "Added new card 32964932432323.");
    }

    @Test
    public void acceptVisitorTest(){
        addCardCommand.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitTransaction(addCardCommand);
    }

    @AfterEach
    public void clearData(){
        addCardCommand = null;
    }
}
