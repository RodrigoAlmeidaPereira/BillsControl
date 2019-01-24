package br.com.billscontrol.api.paymenttype;

import br.com.billscontrol.api.financialcontrol.FinancialControlVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class PaymentTypeVO {
	
	private Long id;

	@NotNull
	private String name;

	private String description;

	@NotNull
	private FinancialControlVO financialControlVO;

}
