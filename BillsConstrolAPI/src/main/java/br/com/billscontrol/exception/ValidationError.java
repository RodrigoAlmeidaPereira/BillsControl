package br.com.billscontrol.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.Collection;

@Data
@AllArgsConstructor
@Builder
public class ValidationError {

    private Integer errorCode;
    private String status;
    private String message;
    private Instant instant;

    private Collection<FieldMessage> errors;
}
