package com.company;

import com.company.BankProduct.Account;
import com.company.Report.ReportVisitor;
import com.company.Transaction.ConcreteCommands.RemoveCard;
import com.company.Transaction.TransactionType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RemoveCardTest {
    public RemoveCard removeCard;

    @Mock
    Account _Account1;
    @Mock
    ReportVisitor _ReportVisitor;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        removeCard = new RemoveCard(TransactionType.REMOVECARD, _Account1,111111111L);
    }

    @Test
    public void executeTest(){
        removeCard.execute();
        verify(_Account1, times(1)).removeCard(111111111L);
    }

    @Test
    public void createDescriptionTest(){
        removeCard.execute();
        assertEquals(removeCard.getDescription(), "Removed card 111111111.");
    }

    @Test
    public void acceptVisitorTest(){
        removeCard.acceptVisitor(_ReportVisitor);
        verify(_ReportVisitor, times(1)).visitTransaction(removeCard);
    }

    @AfterEach
    public void clearData(){
        removeCard = null;
    }
}
