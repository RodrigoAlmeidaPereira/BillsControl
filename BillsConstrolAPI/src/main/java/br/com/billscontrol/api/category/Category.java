package br.com.billscontrol.api.category;

import java.time.Instant;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import br.com.billscontrol.api.transaction.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "category")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Category {

	@Id @GeneratedValue
	private Long id;

	private String name;
	private String description;
	
	@OneToMany(mappedBy = "category")
	private Collection<Transaction> transactions;
	
	@Column(name = "create_user")
	private String createUser;
	
	@Column(name = "create_instant")
	private Instant createInstant;

	@Column(name = "last_update_user")
	private String lastUpdateUser;
	
	@Column(name = "last_update_instant")
	private Instant lastUpdateInstant;
	
}
