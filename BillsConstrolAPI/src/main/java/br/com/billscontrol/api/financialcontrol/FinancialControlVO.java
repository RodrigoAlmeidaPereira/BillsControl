package br.com.billscontrol.api.financialcontrol;

import br.com.billscontrol.api.user.UserVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder =  true)
@Data
public class FinancialControlVO {

    private Long id;

    @NotNull(message = "Name must be specified")
    @Size(min = 1, max = 90, message = "Name must have 1 to 90 characters")

    private String name;

    @NotNull(message = "Description must be specified")
    @Size(min = 1, max = 90, message = "Description must have 1 to 90 characters")
    private String description;

    @NotNull(message = "User must be specified")
    private UserVO userVO;

}
