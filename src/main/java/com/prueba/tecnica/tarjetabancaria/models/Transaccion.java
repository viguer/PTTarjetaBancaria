package com.prueba.tecnica.tarjetabancaria.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter  @Setter
public class Transaccion {

    @Id
    private String idTransaccion;

    @ManyToOne()
    @JoinColumn(name = "idTarjeta")
    private Tarjeta idTarjeta;
    private float valorTransaccion;
    private LocalDateTime fechaTransaccion;
    private String estado;

    @Override
    public String toString() {
        return "Transaccion{" +
                "idTransaccion='" + idTransaccion + '\'' +
                ", idTarjeta=" + idTarjeta.getIdTarjeta() +
                ", valorTransaccion=" + valorTransaccion +
                ", fechaTransaccion=" + fechaTransaccion +
                ", estado='" + estado + '\'' +
                '}';
    }
}
