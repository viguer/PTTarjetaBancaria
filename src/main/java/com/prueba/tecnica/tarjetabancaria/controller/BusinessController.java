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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api",
        method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE})
@CrossOrigin
public class BusinessController {

    @Autowired
    TarjetaService tarjetaService;
    @Autowired
    TransaccionService transaccionService;

    @GetMapping("/card/{productId}/number")
    public ResponseEntity<EmitirTarjetaResponse> emitirTarjeta(@PathVariable("productId") Integer productId) {
        EmitirTarjetaResponse emitirTarjetaResponse = new EmitirTarjetaResponse();
        try {
            Tarjeta emitirTarjeta = tarjetaService.emitirTarjeta(productId);
            emitirTarjetaResponse.setStatusCode(200);
            emitirTarjetaResponse.setMessage("Se emitio Tarjeta Correctamente");
            emitirTarjetaResponse.setCardId(emitirTarjeta.getIdTarjeta());
            return new ResponseEntity<>(emitirTarjetaResponse, HttpStatus.OK);
        } catch (Exception e) {
            emitirTarjetaResponse.setStatusCode(500);
            emitirTarjetaResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(emitirTarjetaResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping(value = "/card/enroll", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TarjetaEnrollResponse> tarjetaEnroll(@RequestBody TarjetaEnrollRequest tarjetaEnrollRequest) {
        System.out.println("Entrada: " + tarjetaEnrollRequest.getCardId());
        TarjetaEnrollResponse tarjetaEnrollResponse = new TarjetaEnrollResponse();
        try {
            tarjetaService.tarjetaEnroll(tarjetaEnrollRequest.getCardId());
            tarjetaEnrollResponse.setStatusCode(200);
            tarjetaEnrollResponse.setMessage("Producto enrolado y activado correctamente");
            return new ResponseEntity<>(tarjetaEnrollResponse, HttpStatus.OK);
        } catch (Exception e) {
            tarjetaEnrollResponse.setStatusCode(500);
            tarjetaEnrollResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(tarjetaEnrollResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/card/{cardId}")
    public ResponseEntity<TarjetaDeleteResponse> deleteTarjeta(@PathVariable("cardId") String cardId) {
        TarjetaDeleteResponse tarjetaDeleteResponse = new TarjetaDeleteResponse();
        try {
            tarjetaService.deleteTarjeta(cardId);
            tarjetaDeleteResponse.setStatusCode(200);
            tarjetaDeleteResponse.setMessage("Tarjeta Bloqueada y eliminada correctamente");
            return new ResponseEntity<>(tarjetaDeleteResponse, HttpStatus.OK);
        } catch (Exception e) {
            tarjetaDeleteResponse.setStatusCode(500);
            tarjetaDeleteResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(tarjetaDeleteResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/card/balance")
    public ResponseEntity<RecargaSaldoResponse> recargarSaldo(@RequestBody RecargaSaldoRequest recargaSaldoRequest) {
        RecargaSaldoResponse recargaSaldoResponse = new RecargaSaldoResponse();
        try {
            tarjetaService.recargarSaldo(recargaSaldoRequest.getCardId(), recargaSaldoRequest.getBalance());
            recargaSaldoResponse.setStatusCode(200);
            recargaSaldoResponse.setMessage("Recarga realizada con exito");
            return new ResponseEntity<>(recargaSaldoResponse, HttpStatus.OK);
        } catch (Exception e) {
            recargaSaldoResponse.setStatusCode(500);
            recargaSaldoResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(recargaSaldoResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/card/balance/{cardId}")
    public ResponseEntity<ConsultaSaldoResponse> consultarSaldo(@PathVariable("cardId") String cardId) {
        ConsultaSaldoResponse consultaSaldoResponse = new ConsultaSaldoResponse();
        try {
            Tarjeta saldoTarjeta = tarjetaService.consultarSaldo(cardId);
            consultaSaldoResponse.setStatusCode(200);
            consultaSaldoResponse.setMessage("saldo consultado correctamente");
            consultaSaldoResponse.setSaldo(saldoTarjeta.getSaldo());
            return new ResponseEntity<>(consultaSaldoResponse, HttpStatus.OK);
        } catch (Exception e) {
            consultaSaldoResponse.setStatusCode(500);
            consultaSaldoResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(consultaSaldoResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/transaction/purchase")
    public ResponseEntity<RealizarTransaccionResponse> realizarTransaction(@RequestBody RealizarTransaccionRequest realizarTransaccionRequest) {
        RealizarTransaccionResponse realizarTransaccionResponse = new RealizarTransaccionResponse();
        try {
            Transaccion realizarTransaccion = transaccionService.realizarTransaction(realizarTransaccionRequest.getCardId(), realizarTransaccionRequest.getPrice());
            realizarTransaccionResponse.setStatusCode(200);
            realizarTransaccionResponse.setMessage("La transaccion se encuentra en proceso, tiene 24 horas para anularla");
            realizarTransaccionResponse.setTransaccionId(realizarTransaccion.getIdTransaccion());
            return new ResponseEntity<>(realizarTransaccionResponse, HttpStatus.OK);
        } catch (Exception e) {
            realizarTransaccionResponse.setStatusCode(500);
            realizarTransaccionResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(realizarTransaccionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<ConsultaTransaccionResponse> consultarTransaccion(@PathVariable("transactionId") String transactionId) {
        ConsultaTransaccionResponse consultaTransaccionResponse = new ConsultaTransaccionResponse();
        try {
            Transaccion consultarTransaccion = transaccionService.consultarTransaccion(transactionId);
            consultaTransaccionResponse.setStatusCode(200);
            consultaTransaccionResponse.setMessage("consulta de Transaccion Exitosa");
            consultaTransaccionResponse.setValor(consultarTransaccion.getValorTransaccion());
            consultaTransaccionResponse.setCardId(consultarTransaccion.getIdTarjeta().getIdTarjeta());
            consultaTransaccionResponse.setTransaccionId(consultarTransaccion.getIdTransaccion());
            consultaTransaccionResponse.setFechaTransaccion(consultarTransaccion.getFechaTransaccion());

            return new ResponseEntity<>(consultaTransaccionResponse, HttpStatus.OK);
        } catch (Exception e) {
            consultaTransaccionResponse.setStatusCode(500);
            consultaTransaccionResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/transaction/anulation")
    public ResponseEntity<AnularTransaccionResponse> anularTransaccion(@RequestBody AnularTransaccionRequest anularTransaccionRequest) {
        AnularTransaccionResponse anularTransaccionResponse = new AnularTransaccionResponse();
        try {
            transaccionService.anularTransaccion(anularTransaccionRequest.getTransactionId(), anularTransaccionRequest.getCardId());
            anularTransaccionResponse.setStatusCode(200);
            anularTransaccionResponse.setMessage("Anulacion ejecutada correctamente");
            return new ResponseEntity<>(anularTransaccionResponse, HttpStatus.OK);
        } catch (Exception e) {
            anularTransaccionResponse.setStatusCode(500);
            anularTransaccionResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
