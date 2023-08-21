package com.prueba.tecnica.tarjetabancaria.controller.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class RealizarTransaccionResponseTest {

    @InjectMocks
    RealizarTransaccionResponse realizarTransaccionResponse;
    Integer statusCode = 200;
    String message = "message";
    String transaccionId = "transaccionId";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        realizarTransaccionResponse.setStatusCode(statusCode);
        realizarTransaccionResponse.setMessage(message);
        realizarTransaccionResponse.setTransaccionId(transaccionId);
    }

    @Test
    void getStatusCode() {
        assertEquals(realizarTransaccionResponse.getStatusCode(), statusCode);
    }

    @Test
    void getMessage() {
        assertEquals(realizarTransaccionResponse.getMessage(), message);
    }

    @Test
    void getTransaccionId() {
        assertEquals(realizarTransaccionResponse.getTransaccionId(), transaccionId);
    }
}