package com.prueba.tecnica.tarjetabancaria.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TransaccionTest {

    @InjectMocks
    Transaccion transaccion;

    String transaccionId = "transaccionId";
    Tarjeta idTarjeta;
    float valorTransaccion = 100f;
    LocalDateTime fechaTransaccion = LocalDateTime.of(2022, 10, 31, 12, 30);
    String estado = "estado";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        idTarjeta = new Tarjeta();
        idTarjeta.setIdTarjeta("idTarjeta");
        transaccion.setIdTransaccion(transaccionId);
        transaccion.setIdTarjeta(idTarjeta);
        transaccion.setEstado(estado);
        transaccion.setFechaTransaccion(fechaTransaccion);
        transaccion.setValorTransaccion(valorTransaccion);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testToString() {
        assertEquals(transaccion.toString(), "Transaccion{idTransaccion='transaccionId', idTarjeta=idTarjeta, valorTransaccion=100.0, fechaTransaccion=2022-10-31T12:30, estado='estado'}");
    }

    @Test
    void getIdTransaccion() {
        assertEquals(transaccion.getIdTransaccion(), transaccionId);
    }

    @Test
    void getIdTarjeta() {
        assertEquals(transaccion.getIdTarjeta(), idTarjeta);
    }

    @Test
    void getValorTransaccion() {
        assertEquals(transaccion.getValorTransaccion(), valorTransaccion);
    }

    @Test
    void getFechaTransaccion() {
        assertEquals(transaccion.getFechaTransaccion(), fechaTransaccion);
    }

    @Test
    void getEstado() {
        assertEquals(transaccion.getEstado(), estado);
    }
}