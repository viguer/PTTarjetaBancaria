package com.prueba.tecnica.tarjetabancaria.controller.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class RecargaSaldoResponseTest {

    @InjectMocks
    RecargaSaldoResponse recargaSaldoResponse;

    Integer statusCode = 200;
    String message = "message";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        recargaSaldoResponse.setStatusCode(statusCode);
        recargaSaldoResponse.setMessage(message);
    }

    @Test
    void getStatusCode() {
        assertEquals(recargaSaldoResponse.getStatusCode(), statusCode);
    }

    @Test
    void getMessage() {
        assertEquals(recargaSaldoResponse.getMessage(), message);
    }
}