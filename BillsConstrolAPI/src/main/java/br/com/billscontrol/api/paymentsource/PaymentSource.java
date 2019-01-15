package br.com.billscontrol.api.paymentsource;

import br.com.billscontrol.api.account.Account;
import br.com.billscontrol.api.financialcontrol.FinancialControl;
import br.com.billscontrol.api.paymenttype.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity(name = "payment_source")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class PaymentSource {
	
	@Id @GeneratedValue
	private Long id;

	@NotNull
	private String name;

	private String description;

	@NotNull
	private PaymentOperation operation;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "account_id")
	private Account account;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "payment_type_id")
	private PaymentType paymentType;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "financial_control_id")
	private FinancialControl financialControl;

	@NotNull
	@Column(name = "create_user")
	private String createUser;

	@NotNull
	@Column(name = "create_instant")
	private Instant createInstant;

	@Column(name = "last_update_user")
	private String lastUpdateUser;
	
	@Column(name = "last_update_instant")
	private Instant lastUpdateInstant;
}
