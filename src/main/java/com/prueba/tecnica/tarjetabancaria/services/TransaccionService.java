package com.prueba.tecnica.tarjetabancaria.services;

import com.prueba.tecnica.tarjetabancaria.models.Tarjeta;
import com.prueba.tecnica.tarjetabancaria.models.Transaccion;
import com.prueba.tecnica.tarjetabancaria.repositories.TarjetaRepository;
import com.prueba.tecnica.tarjetabancaria.repositories.TransaccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.Random;

@Service
public class TransaccionService {

    @Autowired
    TransaccionRepository transaccionRepository;

    @Autowired
    TarjetaRepository tarjetaRepository;

    public Transaccion realizarTransaction(String cardId, float valor) {
        try {
            Random random = new Random();
            long randomNumber = random.nextLong() % 10000000000000000L;
            if (randomNumber < 0) {
                randomNumber *= -1;
            }
            String randomString = String.format("%010d", randomNumber);

            Optional<Tarjeta> tarjeta = tarjetaRepository.findById(cardId);
            if (tarjeta.isEmpty() || !tarjeta.get().getEstado().equals("Activada"))
                throw new Error("Tarjeta no existe o se encuentra Bloqueada");

            if ((tarjeta.get().getSaldo() - valor) < 0)
                throw new Error("Saldo insuficiente para realizar la transaccion");

            tarjeta.get().setSaldo(tarjeta.get().getSaldo() - valor);
            Transaccion transaccion = new Transaccion();
            transaccion.setIdTarjeta(tarjeta.get());
            transaccion.setFechaTransaccion(LocalDateTime.now());
            transaccion.setValorTransaccion(valor);
            transaccion.setIdTransaccion(randomString);
            transaccion.setEstado("Realizada");
            return transaccionRepository.save(transaccion);
        } catch (Exception e) {
            throw e;
        }
    }

    public Transaccion consultarTransaccion(String transactionId) {
        try {
            Optional<Transaccion> transaccion = transaccionRepository.findById(transactionId);
            if (transaccion.isEmpty())
                throw new Error("Transaccion no Existe");

            return transaccion.get();
        } catch (Exception e) {
            throw e;
        }
    }

    public Transaccion anularTransaccion(String transactionId, String cardId) {
        try {
            Optional<Transaccion> transaccion = transaccionRepository.findById(transactionId);
            if (transaccion.isEmpty())
                throw new Error("Transaccion no Existe");

            String pruebas = cardId + "  " + transaccion.get().getIdTarjeta().getIdTarjeta();

            if (cardId.equals(transaccion.get().getIdTarjeta().getIdTarjeta())
                    && transaccion.get().getFechaTransaccion().until(LocalDateTime.now(), ChronoUnit.HOURS) < 24) {
                transaccion.get().setEstado("Anulado");
                transaccion.get().getIdTarjeta().setSaldo(transaccion.get().getIdTarjeta().getSaldo() + transaccion.get().getValorTransaccion());
                return transaccionRepository.save(transaccion.get());
            } else {
                throw new Error("Datos de entrada incorrectos o Transaccion ya cumplio mas de 24 horas y no se puede anular");
            }
        } catch (Exception e) {
            throw e;
        }
    }
}
