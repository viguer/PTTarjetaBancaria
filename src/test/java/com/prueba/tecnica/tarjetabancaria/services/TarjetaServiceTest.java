package com.prueba.tecnica.tarjetabancaria.services;

import com.prueba.tecnica.tarjetabancaria.models.Tarjeta;
import com.prueba.tecnica.tarjetabancaria.repositories.TarjetaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TarjetaServiceTest {

    @Mock
    private TarjetaRepository tarjetaRepository;

    @InjectMocks
    private TarjetaService tarjetaService;

    Tarjeta mockResponseTarjeta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LocalDateTime specificDateTime = LocalDateTime.of(2022, 10, 31, 12, 30);

        mockResponseTarjeta = new Tarjeta();
        mockResponseTarjeta.setIdTarjeta("mockTarjetaId");
        mockResponseTarjeta.setEstado("Activada");
        mockResponseTarjeta.setSaldo(2000000);
        mockResponseTarjeta.setFechaVencimiento("12/2020");
        mockResponseTarjeta.setFechaCreacion(specificDateTime);
        mockResponseTarjeta.setFechaModificacion(specificDateTime);
        mockResponseTarjeta.setNombreTitular("Pepito Perez");

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void emitirTarjeta() {
        Integer productId = 123456;
        mockResponseTarjeta.setEstado("Emitida");
        when(tarjetaRepository.save(any(Tarjeta.class))).thenReturn(mockResponseTarjeta);
        assertNotNull(tarjetaService.emitirTarjeta(productId));

    }

    @Test
    public void emitirTarjetaIncorrectProductId() {
        Integer productId = 456;
        assertThrows(Error.class, () -> tarjetaService.emitirTarjeta(productId));
    }

    @Test
    public void emitirTarjetaThrowsException() {
        Integer productId = 123456;
        when(tarjetaRepository.save(any(Tarjeta.class))).thenThrow(new RuntimeException("Some error occurred"));
        assertThrows(RuntimeException.class, () -> tarjetaService.emitirTarjeta(productId));
    }


    @Test
    void tarjetaEnroll() {
        String cardId = "mockTarjetaId";
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.of(mockResponseTarjeta));
        when(tarjetaRepository.save(any(Tarjeta.class))).thenReturn(mockResponseTarjeta);
        assertNotNull(tarjetaService.tarjetaEnroll(cardId));
    }

    @Test
    public void tarjetaEnrollTarjetaInexistente() {
        String cardId = "mockTarjetaIdInexistente";
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.empty());
        assertThrows(Error.class, () -> tarjetaService.tarjetaEnroll(cardId));
    }

    @Test
    public void tarjetaEnrollThrowsException() {
        String productId = "123456";
        when(tarjetaRepository.findById(productId)).thenThrow(new RuntimeException("Some error occurred"));
        assertThrows(RuntimeException.class, () -> tarjetaService.tarjetaEnroll(productId));
    }

    @Test
    void deleteTarjeta() {
        String cardId = "mockTarjetaId";
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.of(mockResponseTarjeta));
        when(tarjetaRepository.save(any(Tarjeta.class))).thenReturn(mockResponseTarjeta);
       assertNotNull(tarjetaService.deleteTarjeta(cardId));
    }

    @Test
    public void deleteTarjetaNonExistingCard() {
        String cardId = "mockTarjetaIdNoExists";
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.empty());
        assertThrows(Error.class, () -> tarjetaService.deleteTarjeta(cardId));
    }

    @Test
    public void deleteTarjetaThrowsException() {
        String productId = "mockTarjetaId";
        when(tarjetaRepository.findById(productId)).thenThrow(new RuntimeException("Some error occurred"));
        assertThrows(RuntimeException.class, () -> tarjetaService.deleteTarjeta(productId));
    }

    @Test
    void recargarSaldo() {
        String cardId = "mockTarjetaId";
        float valor = 100.0f;
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.of(mockResponseTarjeta));
        when(tarjetaRepository.save(any(Tarjeta.class))).thenReturn(mockResponseTarjeta);
        assertNotNull(tarjetaService.recargarSaldo(cardId, valor));
    }

    @Test
    public void recargarSaldoNonExistingCard() {
        String cardId = "mockTarjetaIdNoExists";
        float valor = 100.0f;
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.empty());
        assertThrows(Error.class, () -> tarjetaService.recargarSaldo(cardId, valor));
    }

    @Test
    public void recargarSaldoBlockedCard() {
        String cardId = "mockTarjetaId";
        float valor = 100.0f;
        mockResponseTarjeta.setEstado("Bloqueada");
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.of(mockResponseTarjeta));
        assertThrows(Error.class, () -> tarjetaService.recargarSaldo(cardId, valor));
    }

    @Test
    public void recargarSaldoThrowsException() {
        String cardId = "mockTarjetaId";
        float valor = 100.0f;
        when(tarjetaRepository.findById(cardId)).thenThrow(new RuntimeException("Some error occurred"));
        assertThrows(RuntimeException.class, () -> tarjetaService.recargarSaldo(cardId, valor));
    }

    @Test
    void consultarSaldo() {
        String cardId = "mockTarjetaId";
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.of(mockResponseTarjeta));
        assertNotNull(tarjetaService.consultarSaldo(cardId));

    }

    @Test
    public void consultarSaldoNonExistingCard() {
        String cardId = "mockTarjetaId";
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.empty());
        assertThrows(Error.class, () -> tarjetaService.consultarSaldo(cardId));
    }

    @Test
    public void consultarSaldoException() {
        String cardId = "mockTarjetaId";
        when(tarjetaRepository.findById(cardId)).thenThrow(new RuntimeException("Some error occurred"));
        assertThrows(RuntimeException.class, () -> tarjetaService.consultarSaldo(cardId));
    }
}