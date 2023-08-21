package com.prueba.tecnica.tarjetabancaria.controller.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RecargaSaldoRequest {
    private String cardId;
    private float balance;
}
