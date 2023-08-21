package com.prueba.tecnica.tarjetabancaria.models;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TarjetaTest {

    @InjectMocks
    Tarjeta tarjeta;

    String tarjetaId = "tarjetaId";
    String nombreTitular = "nombreTitular";
    String fechaVencimiento = "fechaVencimiento";
    float saldo = 100f;
    String estado = "estado";
    LocalDateTime fechaCreacion = LocalDateTime.of(2022, 10, 31, 12, 30);
    LocalDateTime fechaModificacion = LocalDateTime.of(2022, 10, 31, 12, 30);
    Transaccion transaccion = new Transaccion();
    List<Transaccion> listTransacciones= new ArrayList<>();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        listTransacciones.add(transaccion);
        tarjeta.setIdTarjeta(tarjetaId);
        tarjeta.setNombreTitular(nombreTitular);
        tarjeta.setFechaVencimiento(fechaVencimiento);
        tarjeta.setSaldo(saldo);
        tarjeta.setEstado(estado);
        tarjeta.setFechaCreacion(fechaCreacion);
        tarjeta.setFechaModificacion(fechaModificacion);
        tarjeta.setTransacciones(listTransacciones);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testToString() {

        assertEquals(tarjeta.toString(),"Tarjeta{idTarjeta='tarjetaId', nombreTitular='nombreTitular', fechaVencimiento='fechaVencimiento', saldo=100.0, estado='estado', fechaCreacion=2022-10-31T12:30, fechaModificacion=2022-10-31T12:30}");
    }

    @Test
    void setGetIdTarjeta() {
        assertEquals(tarjeta.getIdTarjeta(), tarjetaId);
    }

    @Test
    void setGetNombreTitular() {
        assertEquals(tarjeta.getNombreTitular(), nombreTitular);
    }

    @Test
    void setGetFechaVencimiento() {
        assertEquals(tarjeta.getFechaVencimiento(), fechaVencimiento);
    }

    @Test
    void setGetSaldo() {
        assertEquals(tarjeta.getSaldo(), saldo);
    }

    @Test
    void setGetEstado() {
        assertEquals(tarjeta.getEstado(), estado);
    }

    @Test
    void setGetFechaCreacion() {
        assertEquals(tarjeta.getFechaCreacion(), fechaCreacion);
    }

    @Test
    void setGetFechaModificacion() {
        assertEquals(tarjeta.getFechaModificacion(), fechaModificacion);
    }

    @Test
    void setGetTransacciones() {
        assertEquals(tarjeta.getTransacciones(), listTransacciones);
    }
}