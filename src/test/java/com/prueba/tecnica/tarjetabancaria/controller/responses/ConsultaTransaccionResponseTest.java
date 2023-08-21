package com.prueba.tecnica.tarjetabancaria.controller.responses;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ConsultaTransaccionResponseTest {

    @InjectMocks
    ConsultaTransaccionResponse consultaTransaccionResponse;
    Integer statusCode = 200;
    String message = "message";
    float valor = 100f;
    String cardId = "cardId";
    String transaccionId = "TransaccionId";
    LocalDateTime fechaTransaccion = LocalDateTime.of(2022, 10, 31, 12, 30);;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        consultaTransaccionResponse.setCardId(cardId);
        consultaTransaccionResponse.setTransaccionId(transaccionId);
        consultaTransaccionResponse.setFechaTransaccion(fechaTransaccion);
        consultaTransaccionResponse.setMessage(message);
        consultaTransaccionResponse.setStatusCode(statusCode);
        consultaTransaccionResponse.setValor(valor);
    }

    @Test
    void getStatusCode() {
        assertEquals(consultaTransaccionResponse.getStatusCode(), statusCode);
    }

    @Test
    void getMessage() {
        assertEquals(consultaTransaccionResponse.getMessage(), message);
    }

    @Test
    void getValor() {
        assertEquals(consultaTransaccionResponse.getValor(), valor);
    }

    @Test
    void getCardId() {
        assertEquals(consultaTransaccionResponse.getCardId(), cardId);
    }

    @Test
    void getTransaccionId() {
        assertEquals(consultaTransaccionResponse.getTransaccionId(), transaccionId);
    }

    @Test
    void getFechaTransaccion() {
        assertEquals(consultaTransaccionResponse.getFechaTransaccion(), fechaTransaccion);
    }
}