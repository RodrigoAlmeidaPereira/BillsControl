package br.com.billscontrol.api.financialcontrol;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder =  true)
@Data
public class FinancialControlVO {

    private Long id;
    private String name;
    private String description;

}
