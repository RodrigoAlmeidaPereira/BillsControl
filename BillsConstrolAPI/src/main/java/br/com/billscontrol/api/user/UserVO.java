package br.com.billscontrol.api.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserVO {

    private Long id;

    @NotNull
    private String name;

    @NotNull(message = "Name must be specified")
    @Size(min = 1, max = 90, message = "Name must have 1 to 90 characters")
    @Email(message = "Invalid e-mail")
    private String email;

    @NotNull
    private UserType userType;
}
