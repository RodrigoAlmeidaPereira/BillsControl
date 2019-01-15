package br.com.billscontrol.api.transaction;

import br.com.billscontrol.api.category.Category;
import br.com.billscontrol.api.financialcontrol.FinancialControl;
import br.com.billscontrol.api.installment.Installment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

@Entity(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Transaction {
	
	@Id @GeneratedValue
	private Long id;

	@NotNull
	private BigDecimal value;

	private Date expireDate;
	private Date effectiveDate;

	@NotNull
	@Column(name = "transaction_status")
	private TransactionStatus transactionStatus;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "financial_control_id")
	private FinancialControl financialControl;

	@OneToMany(mappedBy = "transaction")
	private Collection<Installment> installments;

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
