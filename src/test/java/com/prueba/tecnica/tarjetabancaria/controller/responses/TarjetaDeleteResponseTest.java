package com.prueba.tecnica.tarjetabancaria.controller.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TarjetaDeleteResponseTest {

    @InjectMocks
    TarjetaDeleteResponse tarjetaDeleteResponse;
    Integer statusCode = 200;
    String message = "message";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tarjetaDeleteResponse.setStatusCode(statusCode);
        tarjetaDeleteResponse.setMessage(message);
    }

    @Test
    void getStatusCode() {
        assertEquals(tarjetaDeleteResponse.getStatusCode(), statusCode);
    }

    @Test
    void getMessage() {
        assertEquals(tarjetaDeleteResponse.getMessage(), message);
    }
}