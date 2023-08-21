package com.prueba.tecnica.tarjetabancaria.controller.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class RealizarTransaccionRequestTest {

    @InjectMocks
    RealizarTransaccionRequest realizarTransaccionRequest;
    String cardId = "cardId";
    float price = 100f;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        realizarTransaccionRequest.setCardId(cardId);
        realizarTransaccionRequest.setPrice(price);
    }

    @Test
    void getCardId() {
        assertEquals(realizarTransaccionRequest.getCardId(), cardId);
    }

    @Test
    void getPrice() {
        assertEquals(realizarTransaccionRequest.getPrice(), price);
    }
}