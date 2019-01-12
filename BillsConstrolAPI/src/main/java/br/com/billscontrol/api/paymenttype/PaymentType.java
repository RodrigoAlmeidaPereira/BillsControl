package br.com.billscontrol.api.paymenttype;

import java.time.Instant;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

import br.com.billscontrol.api.account.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "payment_type")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class PaymentType {
	
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	private String description;
	
	@JsonBackReference
	@ManyToMany(mappedBy = "paymentTypes")
	private Collection<Account> accounts;

	@Column(name = "create_user")
	private String createUser;
	
	@Column(name = "create_instant")
	private Instant createInstant;

	@Column(name = "last_update_user")
	private String lastUpdateUser;
	
	@Column(name = "last_update_instant")
	private Instant lastUpdateInstant;
}
