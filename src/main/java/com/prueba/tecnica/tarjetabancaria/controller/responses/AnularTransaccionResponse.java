package com.prueba.tecnica.tarjetabancaria.controller.responses;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnularTransaccionResponse {
    private Integer statusCode;
    private String message;
}
