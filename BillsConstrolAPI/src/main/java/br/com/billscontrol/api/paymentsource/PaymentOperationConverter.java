package br.com.billscontrol.api.paymentsource;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class PaymentOperationConverter implements AttributeConverter<PaymentOperation, String> {

    @Override
    public String convertToDatabaseColumn(PaymentOperation attribute) {
        return attribute.getCode();
    }

    @Override
    public PaymentOperation convertToEntityAttribute(String dbData) {
        return PaymentOperation.getByCode(dbData);
    }
}
