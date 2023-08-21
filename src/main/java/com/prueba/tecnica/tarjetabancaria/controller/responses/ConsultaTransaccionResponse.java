package com.prueba.tecnica.tarjetabancaria.controller.responses;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ConsultaTransaccionResponse {
    private Integer statusCode;
    private String message;
    private float valor;
    private String cardId;
    private String transaccionId;
    private LocalDateTime fechaTransaccion;
}
