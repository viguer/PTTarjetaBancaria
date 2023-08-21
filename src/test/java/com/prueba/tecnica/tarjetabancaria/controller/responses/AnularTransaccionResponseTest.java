package com.prueba.tecnica.tarjetabancaria.controller.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class AnularTransaccionResponseTest {

    @InjectMocks
    AnularTransaccionResponse anularTransaccionResponse;

    Integer statusCode = 200;
    String message = "message";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        anularTransaccionResponse.setStatusCode(statusCode);
        anularTransaccionResponse.setMessage(message);
    }

    @Test
    void getStatusCode() {
        assertEquals(anularTransaccionResponse.getStatusCode(), statusCode);
    }

    @Test
    void getMessage() {
        assertEquals(anularTransaccionResponse.getMessage(), message);
    }
}