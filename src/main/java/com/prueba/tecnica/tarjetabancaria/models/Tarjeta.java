package com.prueba.tecnica.tarjetabancaria.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Tarjeta {

    @Id
    private String idTarjeta;
    private String nombreTitular;
    private String fechaVencimiento;
    private float saldo;
    private String estado;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaModificacion;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaccion> transacciones;

    @Override
    public String toString() {
        return "Tarjeta{" +
                "idTarjeta='" + idTarjeta + '\'' +
                ", nombreTitular='" + nombreTitular + '\'' +
                ", fechaVencimiento='" + fechaVencimiento + '\'' +
                ", saldo=" + saldo +
                ", estado='" + estado + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", fechaModificacion=" + fechaModificacion +
                '}';
    }
}
