package com.prueba.tecnica.tarjetabancaria.controller.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class RecargaSaldoRequestTest {

    @InjectMocks
    RecargaSaldoRequest recargaSaldoRequest;

    String cardId = "cardId";
    float balance = 100f;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recargaSaldoRequest.setBalance(balance);
        recargaSaldoRequest.setCardId(cardId);
    }

    @Test
    void getCardId() {
        assertEquals(recargaSaldoRequest.getCardId(), cardId);
    }

    @Test
    void getBalance() {
        assertEquals(recargaSaldoRequest.getBalance(), balance);
    }
}