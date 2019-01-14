package br.com.billscontrol.api.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
public enum UserType {

    ADMIN(0, "Administrador do Sistema"),
    TESTER(1, "Tester"),
    CUSTOMER(2 , "Usuario padrao");

    @Getter
    private Integer code;

    @Getter
    private String description;

    public static UserType getByCode(Integer code) {

        return Arrays.asList(UserType.values())
                .stream()
                .filter(userType -> userType.getCode().equals(code))
                .findFirst()
                .orElse(null);
    }
}
