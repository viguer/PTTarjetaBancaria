package com.prueba.tecnica.tarjetabancaria.controller.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RealizarTransaccionRequest {
    private String cardId;
    private float price;
}
