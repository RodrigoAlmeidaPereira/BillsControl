package br.com.billscontrol.api.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum TransactionStatus {


    PENDIND("PENDING", "Aguardando"),
    PAID("PAID", "Pago"),
    RECEIVED("RECEIVED", "Recebido"),
    EXPIRED("EXPIRED", "Expirado");

    @Getter
    private String status;

    @Getter
    private String description;

    public static TransactionStatus getByStatus(String status) {

        return Arrays.asList(TransactionStatus.values())
                .stream()
                .filter(transactionStatus -> transactionStatus.getStatus().equals(status))
                .findFirst()
                .orElse(null);

    }
}
