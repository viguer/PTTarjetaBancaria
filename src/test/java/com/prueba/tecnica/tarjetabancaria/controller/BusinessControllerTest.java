package com.prueba.tecnica.tarjetabancaria.controller;

import com.prueba.tecnica.tarjetabancaria.controller.requests.AnularTransaccionRequest;
import com.prueba.tecnica.tarjetabancaria.controller.requests.RealizarTransaccionRequest;
import com.prueba.tecnica.tarjetabancaria.controller.requests.RecargaSaldoRequest;
import com.prueba.tecnica.tarjetabancaria.controller.requests.TarjetaEnrollRequest;
import com.prueba.tecnica.tarjetabancaria.controller.responses.*;
import com.prueba.tecnica.tarjetabancaria.models.Tarjeta;
import com.prueba.tecnica.tarjetabancaria.models.Transaccion;
import com.prueba.tecnica.tarjetabancaria.services.TarjetaService;
import com.prueba.tecnica.tarjetabancaria.services.TransaccionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BusinessControllerTest {

    @Mock
    TarjetaService tarjetaService;
    @Mock
    TransaccionService transaccionService;

    @InjectMocks
            BusinessController businessController;

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
    void emitirTarjeta() {
        Integer productId = 123;
        mockResponseTarjeta.setIdTarjeta("1234567890");
        when(tarjetaService.emitirTarjeta(productId)).thenReturn(mockResponseTarjeta);
        EmitirTarjetaResponse expectedResponse = new EmitirTarjetaResponse();
        expectedResponse.setStatusCode(200);
        expectedResponse.setMessage("Se emitio Tarjeta Correctamente");
        expectedResponse.setCardId("1234567890");
        ResponseEntity<EmitirTarjetaResponse> response = businessController.emitirTarjeta(productId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void emitirTarjetaCardIssuanceException() {
        Integer productId = 456;
        when(tarjetaService.emitirTarjeta(productId)).thenThrow(new RuntimeException("Error al emitir la tarjeta"));
        EmitirTarjetaResponse expectedResponse = new EmitirTarjetaResponse();
        expectedResponse.setStatusCode(500);
        expectedResponse.setMessage("Error al emitir la tarjeta");
        ResponseEntity<EmitirTarjetaResponse> response = businessController.emitirTarjeta(productId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void tarjetaEnroll() {
        String cardId = "TarjetaId";
        when(tarjetaService.tarjetaEnroll(cardId)).thenReturn(mockResponseTarjeta);
        TarjetaEnrollRequest tarjetaEnrollRequest = new TarjetaEnrollRequest();
        tarjetaEnrollRequest.setCardId(cardId);
        TarjetaEnrollResponse tarjetaEnrollResponse = new TarjetaEnrollResponse();
        tarjetaEnrollResponse.setStatusCode(200);
        tarjetaEnrollResponse.setMessage("Se emitio Tarjeta Correctamente");
        ResponseEntity<TarjetaEnrollResponse> response = businessController.tarjetaEnroll(tarjetaEnrollRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void tarjetaEnrollIssuanceException() {
        String cardId = "TarjetaId";
        when(tarjetaService.tarjetaEnroll(cardId)).thenThrow(new RuntimeException("Error enroll tarjeta"));
        TarjetaEnrollRequest tarjetaEnrollRequest = new TarjetaEnrollRequest();
        tarjetaEnrollRequest.setCardId(cardId);
        TarjetaEnrollResponse tarjetaEnrollResponse = new TarjetaEnrollResponse();
        tarjetaEnrollResponse.setStatusCode(200);
        tarjetaEnrollResponse.setMessage("Error enroll tarjeta");
        ResponseEntity<TarjetaEnrollResponse> response = businessController.tarjetaEnroll(tarjetaEnrollRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void deleteTarjeta() {
        String cardId = "TarjetaId";
        when(tarjetaService.deleteTarjeta(cardId)).thenReturn(mockResponseTarjeta);
        TarjetaDeleteResponse tarjetaDeleteResponse = new TarjetaDeleteResponse();
        tarjetaDeleteResponse.setStatusCode(200);
        tarjetaDeleteResponse.setMessage("Se elimino correctamente");
        ResponseEntity<TarjetaDeleteResponse> response = businessController.deleteTarjeta(cardId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteTarjetaIssuanceException() {
        String cardId = "TarjetaId";
        when(tarjetaService.deleteTarjeta(cardId)).thenThrow(new RuntimeException("Error eliminando Tarjeta"));
        TarjetaDeleteResponse tarjetaDeleteResponse = new TarjetaDeleteResponse();
        tarjetaDeleteResponse.setStatusCode(200);
        tarjetaDeleteResponse.setMessage("Error eliminando Tarjeta");
        ResponseEntity<TarjetaDeleteResponse> response = businessController.deleteTarjeta(cardId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void recargarSaldo() {
        String cardId = "TarjetaId";
        float valor = 100f;
        when(tarjetaService.recargarSaldo(cardId, valor)).thenReturn(mockResponseTarjeta);
        RecargaSaldoRequest recargaSaldoRequest = new RecargaSaldoRequest();
        recargaSaldoRequest.setCardId(cardId);
        recargaSaldoRequest.setBalance(valor);
        RecargaSaldoResponse recargaSaldoResponse = new RecargaSaldoResponse();
        recargaSaldoResponse.setStatusCode(200);
        recargaSaldoResponse.setMessage("Se recargo correctamente");
        ResponseEntity<RecargaSaldoResponse> response = businessController.recargarSaldo(recargaSaldoRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void recargarSaldoIssuanceException() {
        String cardId = "TarjetaId";
        float valor = 100f;
        when(tarjetaService.recargarSaldo(cardId, valor)).thenThrow(new RuntimeException("Error recargando saldo"));
        RecargaSaldoRequest recargaSaldoRequest = new RecargaSaldoRequest();
        recargaSaldoRequest.setCardId(cardId);
        recargaSaldoRequest.setBalance(valor);
        RecargaSaldoResponse recargaSaldoResponse = new RecargaSaldoResponse();
        recargaSaldoResponse.setStatusCode(500);
        recargaSaldoResponse.setMessage("Error recargando saldo");
        ResponseEntity<RecargaSaldoResponse> response = businessController.recargarSaldo(recargaSaldoRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void consultarSaldo() {
        String cardId = "TarjetaId";
        when(tarjetaService.consultarSaldo(cardId)).thenReturn(mockResponseTarjeta);
        ConsultaSaldoResponse consultaSaldoResponse = new ConsultaSaldoResponse();
        consultaSaldoResponse.setStatusCode(200);
        consultaSaldoResponse.setMessage("Se consulto correctamente");
        consultaSaldoResponse.setSaldo(100f);
        ResponseEntity<ConsultaSaldoResponse> response = businessController.consultarSaldo(cardId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void consultarSaldoIssuanceException() {
        String cardId = "TarjetaId";
        when(tarjetaService.consultarSaldo(cardId)).thenThrow(new RuntimeException("Error consultando saldo"));
        ConsultaSaldoResponse consultaSaldoResponse = new ConsultaSaldoResponse();
        consultaSaldoResponse.setStatusCode(500);
        consultaSaldoResponse.setMessage("Error consultando saldo");
        ResponseEntity<ConsultaSaldoResponse> response = businessController.consultarSaldo(cardId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void realizarTransaction() {
        String cardId = "TarjetaId";
        float valor = 100f;
        when(transaccionService.realizarTransaction(cardId, valor)).thenReturn(mockResponseTransaccion);
        RealizarTransaccionRequest realizarTransaccionRequest = new RealizarTransaccionRequest();
        realizarTransaccionRequest.setCardId(cardId);
        realizarTransaccionRequest.setPrice(valor);
        RealizarTransaccionResponse realizarTransaccionResponse = new RealizarTransaccionResponse();
        realizarTransaccionResponse.setStatusCode(200);
        realizarTransaccionResponse.setMessage("Transaccion realizada correctamente");
        realizarTransaccionResponse.setTransaccionId("TransaccionId");
        ResponseEntity<RealizarTransaccionResponse> response = businessController.realizarTransaction(realizarTransaccionRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void realizarTransactionIssuanceException() {
        String cardId = "TarjetaId";
        float valor = 100f;
        when(transaccionService.realizarTransaction(cardId, valor)).thenThrow(new RuntimeException("Error realizando Transaccion"));
        RealizarTransaccionRequest realizarTransaccionRequest = new RealizarTransaccionRequest();
        realizarTransaccionRequest.setCardId(cardId);
        realizarTransaccionRequest.setPrice(valor);
        RealizarTransaccionResponse realizarTransaccionResponse = new RealizarTransaccionResponse();
        realizarTransaccionResponse.setStatusCode(500);
        realizarTransaccionResponse.setMessage("Error realizando Transaccion");
        ResponseEntity<RealizarTransaccionResponse> response = businessController.realizarTransaction(realizarTransaccionRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void consultarTransaccion() {
        String transactionId = "transactionId";
        when(transaccionService.consultarTransaccion(transactionId)).thenReturn(mockResponseTransaccion);
        ConsultaTransaccionResponse consultaTransaccionResponse = new ConsultaTransaccionResponse();
        consultaTransaccionResponse.setStatusCode(200);
        consultaTransaccionResponse.setMessage("Transaccion realizada correctamente");
        consultaTransaccionResponse.setTransaccionId("transactionId");
        consultaTransaccionResponse.setValor(100f);
        consultaTransaccionResponse.setCardId("tarjetaId");
        consultaTransaccionResponse.setFechaTransaccion(LocalDateTime.now());
        ResponseEntity<ConsultaTransaccionResponse> response = businessController.consultarTransaccion(transactionId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void consultarTransaccionIssuanceException() {
        String transactionId = "transactionId";
        when(transaccionService.consultarTransaccion(transactionId)).thenThrow(new RuntimeException("Error consultando Transaccion"));
        ConsultaTransaccionResponse consultaTransaccionResponse = new ConsultaTransaccionResponse();
        consultaTransaccionResponse.setStatusCode(500);
        consultaTransaccionResponse.setMessage("Error consultando Transaccion");
        ResponseEntity<ConsultaTransaccionResponse> response = businessController.consultarTransaccion(transactionId);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    void anularTransaccion() {
        String transactionId = "transactionId";
        String cardId = "cardId";
        when(transaccionService.anularTransaccion(transactionId, cardId)).thenReturn(mockResponseTransaccion);
        AnularTransaccionRequest anularTransaccionRequest = new AnularTransaccionRequest();
        anularTransaccionRequest.setTransactionId(transactionId);
        anularTransaccionRequest.setCardId(cardId);
        AnularTransaccionResponse anularTransaccionResponse = new AnularTransaccionResponse();
        anularTransaccionResponse.setStatusCode(200);
        anularTransaccionResponse.setMessage("Transaccion anulada correctamente");
        ResponseEntity<AnularTransaccionResponse> response = businessController.anularTransaccion(anularTransaccionRequest);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void anularTransaccionIssuanceException() {
        String transactionId = "transactionId";
        String cardId = "cardId";
        when(transaccionService.anularTransaccion(transactionId, cardId)).thenThrow(new RuntimeException("Error anulando Transaccion"));
        AnularTransaccionRequest anularTransaccionRequest = new AnularTransaccionRequest();
        anularTransaccionRequest.setTransactionId(transactionId);
        anularTransaccionRequest.setCardId(cardId);
        AnularTransaccionResponse anularTransaccionResponse = new AnularTransaccionResponse();
        anularTransaccionResponse.setStatusCode(500);
        anularTransaccionResponse.setMessage("Error anulando Transaccion");
        ResponseEntity<AnularTransaccionResponse> response = businessController.anularTransaccion(anularTransaccionRequest);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}