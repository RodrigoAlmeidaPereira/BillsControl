package br.com.billscontrol.api.bank;

import br.com.billscontrol.api.financialcontrol.FinancialControl;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;

@Entity(name = "bank")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Bank {
	
	@Id @GeneratedValue
	private Long id;

	@NotNull
	private String name;

	private String description;

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
