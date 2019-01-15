package br.com.billscontrol.api.transaction;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TransactionStatusConverter implements AttributeConverter<TransactionStatus, String> {

    @Override
    public String convertToDatabaseColumn(TransactionStatus attribute) {
        return attribute.getStatus();
    }

    @Override
    public TransactionStatus convertToEntityAttribute(String dbData) {
        return TransactionStatus.getByStatus(dbData);
    }
}
