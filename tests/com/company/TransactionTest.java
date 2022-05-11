package com.company;

import com.company.BankProduct.Account;
import com.company.Card.Card;
import com.company.Transaction.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TransactionTest {

    private Transaction transaction;
    @Mock public Card card;
    @Mock public Account account1;
    @Mock public Account account2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

//    @Test
//    void getCardNull() {
//        transaction = new Transaction(account1, account2, 200.00);
//        assertNull(transaction.getCard());
//    }

//    @Test
//    void getCardNotNull() {
//        transaction = new Transaction(account1, account2, card, 200.00);
//        assertNotNull(transaction.getCard());
//    }
}