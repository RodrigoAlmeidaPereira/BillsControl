package br.com.billscontrol.api.user;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserTypeConverter implements AttributeConverter<UserType, Integer> {

    @Override
    public Integer convertToDatabaseColumn(UserType attribute) {
        return attribute.getCode();
    }

    @Override
    public UserType convertToEntityAttribute(Integer dbData) {
        return UserType.getByCode(dbData);
    }
}
