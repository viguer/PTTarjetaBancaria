package com.prueba.tecnica.tarjetabancaria.controller.requests;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AnularTransaccionRequest {

    private String transactionId;
    private String cardId;
}
