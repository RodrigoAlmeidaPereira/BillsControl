package br.com.billscontrol.api.transaction;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import br.com.billscontrol.api.category.Category;
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
	
	@Column(name = "create_user")
	private String createUser;
	
	@Column(name = "create_instant")
	private Instant createInstant;

	@Column(name = "last_update_user")
	private String lastUpdateUser;
	
	@Column(name = "last_update_instant")
	private Instant lastUpdateInstant;
}
