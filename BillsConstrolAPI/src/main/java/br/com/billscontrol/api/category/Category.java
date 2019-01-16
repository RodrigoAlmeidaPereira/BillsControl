package br.com.billscontrol.api.category;

import br.com.billscontrol.api.financialcontrol.FinancialControl;
import br.com.billscontrol.api.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Collection;

@Entity(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Category {

	@Id @GeneratedValue
	private Long id;

	@NotNull
	private String name;

	private String description;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "financial_control_id")
	private FinancialControl financialControl;

	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private Collection<Transaction> transactions;

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
