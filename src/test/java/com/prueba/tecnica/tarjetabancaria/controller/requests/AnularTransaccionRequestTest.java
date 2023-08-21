package com.prueba.tecnica.tarjetabancaria.controller.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AnularTransaccionRequestTest {

    @InjectMocks
    AnularTransaccionRequest anularTransaccionRequest;

    String transactionId = "transactionId";
    String cardId = "cardId";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        anularTransaccionRequest.setTransactionId(transactionId);
        anularTransaccionRequest.setCardId(cardId);
    }

    @Test
    void getTransactionId() {
        assertEquals(anularTransaccionRequest.getTransactionId(), transactionId);
    }

    @Test
    void getCardId() {
        assertEquals(anularTransaccionRequest.getCardId(), cardId);
    }
}