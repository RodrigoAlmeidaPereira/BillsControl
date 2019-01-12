package br.com.billscontrol.api.account;

import java.time.Instant;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import br.com.billscontrol.api.paymenttype.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "account")
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Data
public class Account {
	
	@Id @GeneratedValue
	private Long id;
	
	private String name;
	private String description;
	
	@ManyToMany
	@JsonManagedReference
	@JoinTable( name = "account_payment_type",
		joinColumns = @JoinColumn(name = "account_id"),
		inverseJoinColumns = @JoinColumn(name = "payment_type_id")
	)
	private Collection<PaymentType> paymentTypes;

	@Column(name = "create_user")
	private String createUser;
	
	@Column(name = "create_instant")
	private Instant createInstant;

	@Column(name = "last_update_user")
	private String lastUpdateUser;
	
	@Column(name = "last_update_instant")
	private Instant lastUpdateInstant;
}
