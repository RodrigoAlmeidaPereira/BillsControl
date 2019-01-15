package br.com.billscontrol.api.paymentsource;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum PaymentOperation {

    IN("IN", "Entrada"),
    OUT("OUT", "Saida"),
    IN_OUT("IN/OUT" , "Entrada e Saida");

    @Getter
    private String code;

    @Getter
    private String description;

    public static PaymentOperation getByCode(String code) {

        return Arrays.asList(PaymentOperation.values())
                .stream()
                .filter(operation -> operation.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
