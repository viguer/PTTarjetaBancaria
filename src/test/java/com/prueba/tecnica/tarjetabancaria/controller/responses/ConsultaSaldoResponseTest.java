package com.prueba.tecnica.tarjetabancaria.controller.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaSaldoResponseTest {

    @InjectMocks
    ConsultaSaldoResponse consultaSaldoResponse;

    Integer statusCode = 200;
    String message = "message";
    float saldo = 100f;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consultaSaldoResponse.setStatusCode(statusCode);
        consultaSaldoResponse.setMessage(message);
        consultaSaldoResponse.setSaldo(saldo);
    }

    @Test
    void getStatusCode() {
        assertEquals(consultaSaldoResponse.getStatusCode(), statusCode);
    }

    @Test
    void getMessage() {
        assertEquals(consultaSaldoResponse.getMessage(), message);
    }

    @Test
    void getSaldo() {
        assertEquals(consultaSaldoResponse.getSaldo(), saldo);
    }
}