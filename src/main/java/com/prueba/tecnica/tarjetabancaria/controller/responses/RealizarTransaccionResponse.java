package com.prueba.tecnica.tarjetabancaria.controller.responses;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RealizarTransaccionResponse {
    private Integer statusCode;
    private String message;
    private String transaccionId;
}
