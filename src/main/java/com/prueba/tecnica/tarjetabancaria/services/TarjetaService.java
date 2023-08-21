package com.prueba.tecnica.tarjetabancaria.services;

import com.prueba.tecnica.tarjetabancaria.models.Tarjeta;
import com.prueba.tecnica.tarjetabancaria.repositories.TarjetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class TarjetaService {

    @Autowired
    TarjetaRepository tarjetaRepository;

    private List<Integer> productIds = Arrays.asList(123456, 789012, 456789);

    public Tarjeta emitirTarjeta(Integer productId) {
        try {
            Random random = new Random();
            long randomNumber = random.nextLong() % 10000000000L;
            if (randomNumber < 0) {
                randomNumber *= -1;
            }
            String randomString = String.format("%010d", randomNumber);

            if (!productIds.contains(productId))
                throw new Error("Id de Producto no es Correcto");

            LocalDateTime currentDateTime = LocalDateTime.now();
            LocalDateTime futureDateTime = currentDateTime.plusYears(3);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
            String futureDateAsString = futureDateTime.format(formatter);

            Tarjeta tarjeta = new Tarjeta();
            tarjeta.setIdTarjeta(productId + randomString);
            tarjeta.setFechaVencimiento(futureDateAsString);
            tarjeta.setEstado("Emitida");
            tarjeta.setFechaCreacion(LocalDateTime.now());
            tarjeta.setFechaModificacion(LocalDateTime.now());
            Tarjeta result = tarjetaRepository.save(tarjeta);
            return result;
        } catch (Exception e) {
            throw e;
        }

    }

    public Tarjeta tarjetaEnroll(String cardId) {
        try {
            Optional<Tarjeta> tarjetaEnroll = tarjetaRepository.findById(cardId);
            if (tarjetaEnroll.isEmpty())
                throw new Error("Tarjeta no Existe");

            tarjetaEnroll.get().setFechaModificacion(LocalDateTime.now());
            tarjetaEnroll.get().setEstado("Activada");
            return tarjetaRepository.save(tarjetaEnroll.get());
        } catch (Exception e) {
            throw e;
        }
    }

    public Tarjeta deleteTarjeta(String cardId) {
        try {
            Optional<Tarjeta> tarjeta = tarjetaRepository.findById(cardId);
            if (tarjeta.isEmpty())
                throw new Error("Tarjeta no Existe");

            tarjeta.get().setFechaModificacion(LocalDateTime.now());
            tarjeta.get().setEstado("Bloqueada");
            return tarjetaRepository.save(tarjeta.get());
        } catch (Exception e) {
            throw e;
        }
    }

    public Tarjeta recargarSaldo(String cardId, float valor) {
        try {
            Optional<Tarjeta> tarjeta = tarjetaRepository.findById(cardId);
            if (tarjeta.isEmpty() || tarjeta.get().getEstado() != "Activada")
                throw new Error("Tarjeta no Existe o se ecuentra Bloqueada");

            tarjeta.get().setFechaModificacion(LocalDateTime.now());
            tarjeta.get().setSaldo(tarjeta.get().getSaldo() + valor);
            return tarjetaRepository.save(tarjeta.get());
        } catch (Exception e) {
            throw e;
        }
    }

    public Tarjeta consultarSaldo(String cardId) {
        try {
            Optional<Tarjeta> tarjeta = tarjetaRepository.findById(cardId);
            if (tarjeta.isEmpty())
                throw new Error("Tarjeta no Existe");

            return tarjeta.get();
        } catch (Exception e) {
            throw e;
        }
    }
}
