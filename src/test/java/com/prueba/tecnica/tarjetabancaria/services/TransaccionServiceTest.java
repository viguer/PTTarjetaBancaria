package com.prueba.tecnica.tarjetabancaria.services;

import com.prueba.tecnica.tarjetabancaria.models.Tarjeta;
import com.prueba.tecnica.tarjetabancaria.models.Transaccion;
import com.prueba.tecnica.tarjetabancaria.repositories.TarjetaRepository;
import com.prueba.tecnica.tarjetabancaria.repositories.TransaccionRepository;
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


class TransaccionServiceTest {

    @Mock
    private TransaccionRepository transaccionRepository;

    @Mock
    private TarjetaRepository tarjetaRepository;

    @InjectMocks
    private TransaccionService transaccionService;

    Transaccion mockResponseTransaccion;
    Tarjeta mockResponseTarjeta;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LocalDateTime specificDateTime = LocalDateTime.of(2022, 10, 31, 12, 30);

        mockResponseTransaccion = new Transaccion();
        mockResponseTransaccion.setIdTransaccion("mockIdTransaccion");
        mockResponseTransaccion.setFechaTransaccion(specificDateTime);
        mockResponseTransaccion.setEstado("Realizada");
        mockResponseTransaccion.setValorTransaccion(1000000);

        mockResponseTarjeta = new Tarjeta();
        mockResponseTarjeta.setIdTarjeta("mockTarjetaId");
        mockResponseTarjeta.setEstado("Activa");
        mockResponseTarjeta.setSaldo(2000000);
        mockResponseTarjeta.setFechaVencimiento("12/2020");
        mockResponseTarjeta.setFechaCreacion(specificDateTime);
        mockResponseTarjeta.setFechaModificacion(specificDateTime);
        mockResponseTarjeta.setNombreTitular("Pepito Perez");

        mockResponseTransaccion.setIdTarjeta(mockResponseTarjeta);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void realizarTransaction() {
        String cardId = "123";
        float valor = 100.0f;
        String randomString = "1234567890";

        Random random = mock(Random.class);
        when(random.nextLong()).thenReturn(1234567890L);

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setEstado("Activada");
        tarjeta.setSaldo(200.0f);
        tarjeta.setFechaVencimiento("12/2025");

        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.of(tarjeta));

        Transaccion expectedTransaccion = new Transaccion();
        expectedTransaccion.setIdTarjeta(tarjeta);
        expectedTransaccion.setFechaTransaccion(LocalDateTime.now());
        expectedTransaccion.setValorTransaccion(valor);
        expectedTransaccion.setIdTransaccion(randomString);
        expectedTransaccion.setEstado("Realizada");
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(expectedTransaccion);

        Transaccion actualTransaccion = transaccionService.realizarTransaction(cardId, valor);

        assertNotNull(actualTransaccion);
        assertEquals(expectedTransaccion, actualTransaccion);
        assertEquals(tarjeta.getSaldo(), 100);
    }

    @Test
    void realizarTransactionCardExpired() {
        String cardId = "123";
        float valor = 100.0f;
        String randomString = "1234567890";

        Random random = mock(Random.class);
        when(random.nextLong()).thenReturn(1234567890L);

        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setEstado("Activada");
        tarjeta.setSaldo(200.0f);
        tarjeta.setFechaVencimiento("08/2023");

        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.of(tarjeta));

        Transaccion expectedTransaccion = new Transaccion();
        expectedTransaccion.setIdTarjeta(tarjeta);
        expectedTransaccion.setFechaTransaccion(LocalDateTime.now());
        expectedTransaccion.setValorTransaccion(valor);
        expectedTransaccion.setIdTransaccion(randomString);
        expectedTransaccion.setEstado("Realizada");
        when(transaccionRepository.save(any(Transaccion.class))).thenReturn(expectedTransaccion);

        assertThrows(Error.class, () -> transaccionService.realizarTransaction(cardId, valor));
    }
    @Test
    public void testRealizarTransactionCardNotFound() {
        String cardId = "456";
        float valor = 100.0f;
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.empty());
        assertThrows(Error.class, () -> transaccionService.realizarTransaction(cardId, valor));
    }

    @Test
    public void testRealizarTransactionCardNotActivated() {
        String cardId = "789";
        float valor = 100.0f;
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setEstado("Bloqueada");
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.of(tarjeta));
        assertThrows(Error.class, () -> transaccionService.realizarTransaction(cardId, valor));
    }

    @Test
    public void testRealizarTransactionInsufficientBalance() {
        String cardId = "123";
        float valor = 300.0f;
        Tarjeta tarjeta = new Tarjeta();
        tarjeta.setEstado("Activada");
        tarjeta.setSaldo(200.0f);
        when(tarjetaRepository.findById(cardId)).thenReturn(Optional.of(tarjeta));
        assertThrows(Error.class, () -> transaccionService.realizarTransaction(cardId, valor));
    }

    @Test
    public void testRealizarTransactionThrowsException() {
        String cardId = "123";
        float valor = 100.0f;
        when(tarjetaRepository.findById(cardId)).thenThrow(new RuntimeException("Some error occurred"));
        assertThrows(RuntimeException.class, () -> transaccionService.realizarTransaction(cardId, valor));
    }

    @Test
    void consultarTransaccionSuccessful() {
        when(transaccionRepository.findById("mockIdTransaccion")).thenReturn(Optional.ofNullable(mockResponseTransaccion));
        assert(transaccionService.consultarTransaccion("mockIdTransaccion")).equals(mockResponseTransaccion);
    }

    @Test
    void consultarTransaccionEmpty() {
        when(transaccionRepository.findById("mockIdTransaccionNoExist")).thenReturn(Optional.empty());
        assertThrows(Error.class, () -> transaccionService.consultarTransaccion("mockIdTransaccionNoExist"));
    }

    @Test
    void testConsultarTransaccionThrowsException() {
        when(transaccionRepository.findById("mockIdTransaccion")).thenThrow(new RuntimeException("Some error occurred"));
        assertThrows(RuntimeException.class, () -> transaccionService.consultarTransaccion("mockIdTransaccion"));
    }

    @Test
    void anularTransaccion() {
        mockResponseTransaccion.setFechaTransaccion(LocalDateTime.now().minusHours(12));
        String transactionId = "mockTransaccionId";
        String cardId = "mockTarjetaId";
        Transaccion result = mockResponseTransaccion;
        result.setEstado("Anulado");
        result.getIdTarjeta().setSaldo(result.getIdTarjeta().getSaldo()+ result.getValorTransaccion());

        when(transaccionRepository.findById(transactionId)).thenReturn(Optional.of(mockResponseTransaccion));
        when(transaccionRepository.save(mockResponseTransaccion)).thenReturn(result);
        assertEquals(transaccionService.anularTransaccion(transactionId, cardId), result);
    }

    @Test
    public void testAnularTransaccion_NonExistentTransaction() {
        String transactionId = "mockTransaccionIdNoExist";
        String cardId = "mockTarjetaId";
        when(transaccionRepository.findById(transactionId)).thenReturn(Optional.empty());
        assertThrows(Error.class, () -> transaccionService.anularTransaccion(transactionId, cardId));
    }

    @Test
    public void testAnularTransaccion_IncorrectCardId() {
        String transactionId = "mockTransaccionId";
        String cardId = "mockTarjetaIdIncorrect";
        when(transaccionRepository.findById(transactionId)).thenReturn(Optional.of(mockResponseTransaccion));
        assertThrows(Error.class, () -> transaccionService.anularTransaccion(transactionId, cardId));
    }

    @Test
    public void testAnularTransaccion_TransactionOlderThan24Hours() {
        String transactionId = "mockTransaccionId";
        String cardId = "mockTarjetaId";
        mockResponseTransaccion.setFechaTransaccion(LocalDateTime.now().minusHours(48));
        when(transaccionRepository.findById(transactionId)).thenReturn(Optional.of(mockResponseTransaccion));
        assertThrows(Error.class, () -> transaccionService.anularTransaccion(transactionId, cardId));
    }

    @Test
    public void testAnularTransaccionThrowsException() {

        String transactionId = "mockTransaccionId";
        String cardId = "mockTarjetaId";
        when(transaccionRepository.findById(transactionId)).thenThrow(new RuntimeException("Some error occurred"));
        assertThrows(RuntimeException.class, () -> transaccionService.anularTransaccion(transactionId, cardId));
    }
}