package br.com.billscontrol.api.category;

import br.com.billscontrol.api.financialcontrol.FinancialControlVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class CategoryVO {

    private Long id;

    @NotNull(message = "Name must be specified")
    @Size(min = 1, max = 90, message = "Name must have 1 to 90 characters")
    private String name;

    private String description;

    @NotNull(message = "Financial control must be specified")
    private FinancialControlVO financialControlVO;
}
