package com.company;


import com.company.BankProduct.Account;
import com.company.BankProduct.BankProductStatus;
import com.company.Card.Card;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    public Card card;

    @Mock public Account account;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void setStatusTest() {
        card = new Card("123456789", account, 123L, 123, null);

        assertEquals(card.getStatus(), BankProductStatus.ACTIVE);
        card.setStatus(BankProductStatus.CLOSED);
        assertEquals(card.getStatus(), BankProductStatus.CLOSED);

        card.setStatus(BankProductStatus.CLOSED);
        assertEquals(card.getStatus(), BankProductStatus.CLOSED);

        card.setStatus(BankProductStatus.ACTIVE);
        assertEquals(card.getStatus(), BankProductStatus.ACTIVE);

        card.setStatus(BankProductStatus.ACTIVE);
        assertEquals(card.getStatus(), BankProductStatus.ACTIVE);
    }

    @AfterEach
    void cleanData(){
        account = null;
    }
}