package com.prueba.tecnica.tarjetabancaria.repositories;

import com.prueba.tecnica.tarjetabancaria.models.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransaccionRepository extends JpaRepository<Transaccion, String> {
}
