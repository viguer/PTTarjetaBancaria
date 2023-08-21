package com.prueba.tecnica.tarjetabancaria.controller.requests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TarjetaEnrollRequestTest {

    @InjectMocks
    TarjetaEnrollRequest tarjetaEnrollRequest;
    String cardId = "cardId";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tarjetaEnrollRequest.setCardId(cardId);
    }

    @Test
    void getCardId() {
        assertEquals(tarjetaEnrollRequest.getCardId(), cardId);
    }
}