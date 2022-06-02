package com.company;

import com.company.Transaction.HistoryOfOperations;
import com.company.Transaction.TransactionCommand;
import org.junit.jupiter.api.*;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

public class HistoryOfOperationsTest {
    public HistoryOfOperations historyOfOperations;

    @Mock TransactionCommand _TransactionCommand1;
    @Mock TransactionCommand _TransactionCommand2;

    @BeforeEach
    public void setUp(){
        historyOfOperations = new HistoryOfOperations();
    }

    @Test
    public void addOperationTest(){
        assertEquals(historyOfOperations.getHistory().size(), 0);

        historyOfOperations.addOperation(_TransactionCommand1);
        assertEquals(historyOfOperations.getHistory().size(), 1);

        historyOfOperations.addOperation(_TransactionCommand2);
        assertEquals(historyOfOperations.getHistory().size(), 2);
    }

    @AfterEach
    public void clearData(){
        historyOfOperations = null;
    }
}
