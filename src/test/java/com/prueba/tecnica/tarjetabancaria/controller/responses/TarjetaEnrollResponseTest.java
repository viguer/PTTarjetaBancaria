package com.prueba.tecnica.tarjetabancaria.controller.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TarjetaEnrollResponseTest {

    @InjectMocks
    TarjetaEnrollResponse tarjetaEnrollResponse;
    private Integer statusCode = 200;
    private String message = "message";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tarjetaEnrollResponse.setStatusCode(statusCode);
        tarjetaEnrollResponse.setMessage(message);
    }

    @Test
    void getStatusCode() {
        assertEquals(tarjetaEnrollResponse.getStatusCode(), statusCode);
    }

    @Test
    void getMessage() {
        assertEquals(tarjetaEnrollResponse.getMessage(), message);
    }
}