package com.prueba.tecnica.tarjetabancaria.controller.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class EmitirTarjetaResponseTest {

    @InjectMocks
    EmitirTarjetaResponse emitirTarjetaResponse;

    Integer statusCode = 200;
    String message = "message";
    String cardId = "cardId";



    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        emitirTarjetaResponse.setMessage(message);
        emitirTarjetaResponse.setStatusCode(statusCode);
        emitirTarjetaResponse.setCardId(cardId);
    }

    @Test
    void getStatusCode() {
        assertEquals(emitirTarjetaResponse.getStatusCode(), statusCode);
    }

    @Test
    void getMessage() {
        assertEquals(emitirTarjetaResponse.getMessage(), message);
    }

    @Test
    void getCardId() {
        assertEquals(emitirTarjetaResponse.getCardId(), cardId);
    }
}