package br.com.billscontrol.api.transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

import javax.persistence.*;

import br.com.billscontrol.api.category.Category;
import br.com.billscontrol.api.installment.Installment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "transaction")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Transaction {
	
	@Id @GeneratedValue
	private Long id;
	
	private BigDecimal value;
	private Date movementDate;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;

	@OneToMany(mappedBy = "transaction")
	private Collection<Installment> installments;

	@Column(name = "create_user")
	private String createUser;

	@Column(name = "create_instant")
	private Instant createInstant;

	@Column(name = "last_update_user")
	private String lastUpdateUser;

	@Column(name = "last_update_instant")
	private Instant lastUpdateInstant;


}
