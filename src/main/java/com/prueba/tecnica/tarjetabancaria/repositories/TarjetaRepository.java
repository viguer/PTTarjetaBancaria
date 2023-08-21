package com.prueba.tecnica.tarjetabancaria.repositories;

import com.prueba.tecnica.tarjetabancaria.models.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TarjetaRepository extends JpaRepository <Tarjeta, String> {
}
